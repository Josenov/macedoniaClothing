package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.dto.TransactionDTO;
import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.entities.Store;
import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.ClothingDAO;
import com.macedonia.macedonia.persistence.StoreDAO;
import com.macedonia.macedonia.persistence.TransactionDAO;
import com.macedonia.macedonia.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final ClothingDAO clothingDAO;
    private final StoreDAO storeDAO;

    public TransactionServiceImpl(TransactionDAO transactionDAO, ClothingDAO clothingDAO, StoreDAO storeDAO) {
        this.transactionDAO = transactionDAO;
        this.clothingDAO = clothingDAO;
        this.storeDAO = storeDAO;
    }

    //Conversion de Transaction a TransactionDTO

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO.TransactionDTOBuilder dtoBuilder = TransactionDTO.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .quantity(transaction.getQuantity())
                .notes(transaction.getNotes());

        if (transaction.getClothing() != null) {
            dtoBuilder.clothingId(transaction.getClothing().getId())
                    .clothingName(transaction.getClothing().getName());
        }

        if (transaction.getStore() != null) {
            dtoBuilder.storeId(transaction.getStore().getId())
                    .storeName(transaction.getStore().getName())
                    .storeLocation(transaction.getStore().getLocation());
        }

        return dtoBuilder.build();
    }

    private Transaction mapToEntity (TransactionDTO transactionDTO){
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setType(transactionDTO.getType());
        transaction.setNotes(transactionDTO.getNotes());

        // Asignar Clothing si clothingId no es nulo
        if (transactionDTO.getClothingId() != null) {
            Clothing clothing = clothingDAO.findById(transactionDTO.getClothingId())
                    .orElseThrow(() -> new IllegalArgumentException("La prenda especificada no existe."));
            transaction.setClothing(clothing);
        }

        // Asignar Store si storeId no es nulo
        if (transactionDTO.getStoreId() != null) {
            Store store = storeDAO.findById(transactionDTO.getStoreId())
                    .orElseThrow(() -> new IllegalArgumentException("La tienda especificada no existe."));
            transaction.setStore(store);
        }

        return transaction;
    };

    /*@Override
    public Transaction createTransaction(Transaction transaction) {
        // Validar tienda
        if (transaction.getStore() == null || transaction.getStore().getId() == null) {
            throw new IllegalArgumentException("Debe especificar una tienda válida.");
        }
        Store store = storeDAO.findById(transaction.getStore().getId())
                .orElseThrow(() -> new IllegalArgumentException("La tienda especificada no existe."));

        // Validar prenda si está presente
        Clothing clothing = null;
        if (transaction.getClothing() != null && transaction.getClothing().getId() != null) {
            clothing = clothingDAO.findById(transaction.getClothing().getId())
                    .orElseThrow(() -> new IllegalArgumentException("La prenda especificada no existe."));
        }

        // Asignar la fecha actual si no se proporciona
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        // Asociar las entidades gestionadas
        transaction.setStore(store);
        transaction.setClothing(clothing);

        return transactionDAO.save(transaction);
    }*/

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // Convertir DTO a entidad
        Transaction transaction = mapToEntity(transactionDTO);

        // Validar tienda
        if (transaction.getStore() == null || transaction.getStore().getId() == null) {
            throw new IllegalArgumentException("Debe especificar una tienda válida.");
        }
        Store store = storeDAO.findById(transaction.getStore().getId())
                .orElseThrow(() -> new IllegalArgumentException("La tienda especificada no existe."));
        transaction.setStore(store);

        // Validar cantidad
        int quantity = transactionDTO.getQuantity();
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        transaction.setQuantity(quantity);

        // Validar prenda
        if (transactionDTO.getClothingId() == null) {
            throw new IllegalArgumentException("Debe especificar un ID de prenda válido.");
        }
        Clothing clothing = clothingDAO.findById(transactionDTO.getClothingId())
                .orElseThrow(() -> new IllegalArgumentException("La prenda especificada no existe."));
        transaction.setClothing(clothing);

        // Manejar stock y calcular monto
        if ("COMPRA".equalsIgnoreCase(transaction.getType())) {
            clothing.setStock(clothing.getStock() + quantity);
            clothingDAO.save(clothing);
            transaction.setAmount(quantity * clothing.getPurchasePrice());
        } else if ("VENTA".equalsIgnoreCase(transaction.getType())) {
            if (clothing.getStock() < quantity) {
                throw new IllegalArgumentException("Stock insuficiente para la venta.");
            }
            clothing.setStock(clothing.getStock() - quantity);
            clothingDAO.save(clothing);
            transaction.setAmount(quantity * clothing.getSellingPrice());
        } else {
            throw new IllegalArgumentException("Tipo de transacción inválido. Debe ser COMPRA o VENTA.");
        }

        // Asignar fecha de la transacción
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        // Guardar la transacción y devolver DTO
        Transaction savedTransaction = transactionDAO.save(transaction);
        return mapToDTO(savedTransaction);
    }

    @Override
    public TransactionDTO updatedTransaction(Long id, TransactionDTO transactionDTO) {
        // Buscar la transacción existente
        Transaction existingTransaction = transactionDAO.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Transaction not found with ID: " + id)
        );

        // Validar y actualizar los campos
        if (transactionDTO.getType() != null) {
            existingTransaction.setType(transactionDTO.getType());
        }
        if (transactionDTO.getTransactionDate() != null) {
            existingTransaction.setTransactionDate(transactionDTO.getTransactionDate());
        }
        if (transactionDTO.getNotes() != null) {
            existingTransaction.setNotes(transactionDTO.getNotes());
        }

        // Actualizar quantity si está presente
        if (transactionDTO.getQuantity() != 0) {
            int newQuantity = transactionDTO.getQuantity();

            // Validar stock según el tipo de transacción
            if ("COMPRA".equalsIgnoreCase(existingTransaction.getType())) {
                existingTransaction.getClothing().setStock(
                        existingTransaction.getClothing().getStock() + (newQuantity - existingTransaction.getQuantity())
                );
            } else if ("VENTA".equalsIgnoreCase(existingTransaction.getType())) {
                int stockDifference = existingTransaction.getQuantity() - newQuantity;
                if (stockDifference > existingTransaction.getClothing().getStock()) {
                    throw new IllegalArgumentException("Stock insuficiente para la actualización de la venta.");
                }
                existingTransaction.getClothing().setStock(existingTransaction.getClothing().getStock() + stockDifference);
            }

            // Actualizar quantity en la transacción
            existingTransaction.setQuantity(newQuantity);
        }

        if (transactionDTO.getClothingId() != null) {
            Clothing clothing = clothingDAO.findById(transactionDTO.getClothingId())
                    .orElseThrow(() -> new IllegalArgumentException("Clothing not found with ID: " + transactionDTO.getClothingId()));
            existingTransaction.setClothing(clothing);
        }

        if (transactionDTO.getStoreId() != null) {
            Store store = storeDAO.findById(transactionDTO.getStoreId())
                    .orElseThrow(() -> new IllegalArgumentException("Store not found with ID: " + transactionDTO.getStoreId()));
            existingTransaction.setStore(store);
        }

        // Recalcular el monto de la transacción
        if ("COMPRA".equalsIgnoreCase(existingTransaction.getType())) {
            existingTransaction.setAmount(existingTransaction.getQuantity() * existingTransaction.getClothing().getPurchasePrice());
        } else if ("VENTA".equalsIgnoreCase(existingTransaction.getType())) {
            existingTransaction.setAmount(existingTransaction.getQuantity() * existingTransaction.getClothing().getSellingPrice());
        }

        // Guardar los cambios en la base de datos
        Transaction updatedTransaction = transactionDAO.save(existingTransaction);

        // Convertir la transacción actualizada a DTO y devolverla
        return mapToDTO(updatedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionDAO.deleteById(id);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionDAO.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }

    @Override
    public double getTotalInvestmentByMonth(int year, int month) {
        List<Transaction> purchases = transactionDAO.findByTypeAndDate("COMPRA", year, month);
        return purchases.stream().mapToDouble(Transaction::getAmount).sum();
    }

    @Override
    public double getTotalSalesByMonth(int year, int month) {
        List<Transaction> sales = transactionDAO.findByTypeAndDate("VENTA", year, month);
        return sales.stream().mapToDouble(Transaction::getAmount).sum();
    }
}
