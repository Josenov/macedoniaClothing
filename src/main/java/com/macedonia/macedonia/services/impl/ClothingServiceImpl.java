package com.macedonia.macedonia.services.impl;

import com.macedonia.macedonia.dto.ClothingDTO;
import com.macedonia.macedonia.entities.Clothing;
import com.macedonia.macedonia.entities.Inventory;
import com.macedonia.macedonia.entities.Transaction;
import com.macedonia.macedonia.persistence.ClothingDAO;
import com.macedonia.macedonia.persistence.InventoryDAO;
import com.macedonia.macedonia.persistence.TransactionDAO;
import com.macedonia.macedonia.services.ClothingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class ClothingServiceImpl implements ClothingService {

    private final ClothingDAO clothingDAO;

    private final TransactionDAO transactionDAO;

    private final InventoryDAO inventoryDAO;


    public ClothingServiceImpl(ClothingDAO clothingDAO, TransactionDAO transactionDAO, InventoryDAO inventoryDAO){

        this.clothingDAO=clothingDAO;
        this.transactionDAO=transactionDAO;
        this.inventoryDAO=inventoryDAO;
    };

    /*@Override
    public Clothing createClothing(Clothing clothing) {


        // Verificar si se ha proporcionado un ID de inventario
        if (clothing.getInventory() == null || clothing.getInventory().getId() == null) {
            System.out.println("El objeto Inventory es null.");
            throw new IllegalArgumentException("Debe especificar un ID de inventario válido.");
        }

        // Log para verificar el ID del inventario
        System.out.println("ID del inventario recibido: " + clothing.getInventory().getId());

        // Buscar el inventario por ID
        Inventory inventory = inventoryDAO.findById(clothing.getInventory().getId())
                .orElseThrow(() -> new IllegalArgumentException("El inventario especificado no existe."));

        // Buscar si la prenda ya existe en el inventario
        Clothing existingClothing = clothingDAO.findByNameAndInventoryId(
                clothing.getName(),
                inventory.getId()
        );

        if (existingClothing != null) {
            // Incrementar el stock si ya existe
            existingClothing.setStock(existingClothing.getStock() + 1);
            return clothingDAO.save(existingClothing);
        }

        // Si no existe, asociar la prenda al inventario, establecer el stock inicial y guardarla
        clothing.setInventory(inventory);
        clothing.setStock(1);
        return clothingDAO.save(clothing);
    }*/


    //Mapeo ClothingDTO

    private ClothingDTO mapToDTO(Clothing clothing){
        return new ClothingDTO(
                clothing.getId(),
                clothing.getName(),
                clothing.getSize(),
                clothing.getColor(),
                clothing.getPurchasePrice(),
                clothing.getSellingPrice(),
                clothing.getStock(),
                clothing.getInventoryId() != null ? clothing.getInventoryId() : null
        );


    }

    private Clothing mapToEntity(ClothingDTO clothingDTO){

        Clothing clothing = new Clothing();
        clothing.setId(clothingDTO.getId());
        clothing.setName(clothingDTO.getName());
        clothing.setSize(clothingDTO.getSize());
        clothing.setColor(clothingDTO.getColor());
        clothing.setPurchasePrice(clothingDTO.getPurchasePrice());
        clothing.setSellingPrice(clothingDTO.getSellingPrice());
        clothing.setStock(clothingDTO.getStock());

        return clothing;

    }

    @Override
    public ClothingDTO createClothing(ClothingDTO clothingDTO) {
        if (clothingDTO.getInventoryId() == null) {
            System.out.println("Inventory ID is null!");
            throw new IllegalArgumentException("Debe especificar un ID de inventario válido.");
        }

        // Buscar el inventario por ID
        Inventory inventory = inventoryDAO.findById(clothingDTO.getInventoryId())
                .orElseThrow(() -> new IllegalArgumentException("El inventario especificado no existe."));

        // Convertir DTO a entidad
        Clothing clothing = mapToEntity(clothingDTO);

        // Buscar si la prenda ya existe en el inventario
        Clothing existingClothing = clothingDAO.findByNameAndInventory(clothingDTO.getName(), inventory);

        if (existingClothing != null) {
            // Incrementar el stock si ya existe
            existingClothing.setStock(existingClothing.getStock() + 1);
            return mapToDTO(clothingDAO.save(existingClothing));
        }

        // Si no existe, asociar la prenda al inventario, establecer el stock inicial y guardarla
        clothing.setInventory(inventory);
        clothing.setStock(1);
        return mapToDTO(clothingDAO.save(clothing));
    }

    @Override
    public Clothing updateClothing(Long id, Clothing clothing) {
        return null;
    }

    /*@Override
    public void deleteClothing(Long id) {
        clothingDAO.deleteById(id);
    }*/

    @Override
    @Transactional
    public void deleteClothing(Long id) {
        // Buscar el Clothing por ID
        Clothing clothing = clothingDAO.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Prenda con ID " + id + " no se encuentra en la base de datos o ya fue eliminada")
        );

        // Desvincular transacciones relacionadas
        List<Transaction> transactions = transactionDAO.findByField("clothing.id", id);
        if (!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                transaction.setClothing(null); // Desvincular Clothing
                transactionDAO.save(transaction); // Actualizar transacción
            }
        }

        // Desvincular el Clothing del Inventory (si aplica)
        if (clothing.getInventory() != null) {
            clothing.setInventory(null);
            clothingDAO.save(clothing); // Guardar los cambios en Clothing
        }

        // Eliminar el Clothing
        clothingDAO.deleteById(id);
    }

    @Override
    public Clothing getClothingById(Long id) {
        return clothingDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Clothing not found with ID: " + id)
        );
    }

    @Override
    public List<Clothing> getAllClothing() {
        return clothingDAO.findAll();
    }
}
