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

        // Validar prenda si está presente
        if (transactionDTO.getClothingId() != null) {
            Clothing clothing = clothingDAO.findById(transactionDTO.getClothingId())
                    .orElseThrow(() -> new IllegalArgumentException("La prenda especificada no existe."));

            transaction.setClothing(clothing);

            // Manejar stock basado en el tipo de transacción
            if ("COMPRA".equalsIgnoreCase(transaction.getType())) {
                clothing.setStock(clothing.getStock() + 1); // Incrementar el stock
                clothingDAO.save(clothing); // Actualizar el stock en la base de datos
            } else if ("VENTA".equalsIgnoreCase(transaction.getType())) {
                if (clothing.getStock() < 1) {
                    throw new IllegalArgumentException("Stock insuficiente para la venta.");
                }
                clothing.setStock(clothing.getStock() - 1); // Reducir el stock
                clothingDAO.save(clothing); // Actualizar el stock en la base de datos
            } else {
                throw new IllegalArgumentException("Tipo de transacción inválido. Debe ser COMPRA o VENTA.");
            }
        }

        // Asignar la fecha actual si no se proporciona
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        // Guardar la transacción
        Transaction savedTransaction = transactionDAO.save(transaction);

        // Mapear a DTO simplificado y devolverlo
        return mapToDTO(savedTransaction);
    }

    @Override
    public Transaction updatedTransaction(Long id, Transaction transactionDetails) {
        Transaction existingTransaction = transactionDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Transaction not found with ID: " + id)
        );

        // Actualizar los campos de la transacción
        existingTransaction.setType(transactionDetails.getType());
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setTransactionDate(transactionDetails.getTransactionDate());
        existingTransaction.setNotes(transactionDetails.getNotes());



        return transactionDAO.save(existingTransaction);
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
}
