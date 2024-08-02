package com.team.building.service;

import com.team.building.dtos.TableRestaurantDTO;
import com.team.building.mappers.TableRestaurantMapper;
import com.team.building.model.TableRestaurant;
import com.team.building.model.User;
import com.team.building.repository.TableRestaurantRepository;
import com.team.building.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class TableRestaurantService {
    @Autowired
    private TableRestaurantRepository tableRestaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRestaurantMapper tableRestaurantMapper;


    // Method to create a table
    public TableRestaurantDTO createTable(TableRestaurantDTO dto) {
        TableRestaurant tableRestaurant = new TableRestaurant();
        tableRestaurantMapper.mapFromRest(dto, tableRestaurant);
        tableRestaurant = tableRestaurantRepository.save(tableRestaurant);
        TableRestaurantDTO createdDto = new TableRestaurantDTO();
        tableRestaurantMapper.mapToRest(tableRestaurant, createdDto);
        return createdDto;
    }

    // Method to associate a table with a user
    public TableRestaurantDTO associateTableWithUser(Long tableId, Long userId) {
        Optional<TableRestaurant> tableOptional = tableRestaurantRepository.findById(tableId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (tableOptional.isPresent() && userOptional.isPresent()) {
            TableRestaurant tableRestaurant = tableOptional.get();
            User user = userOptional.get();

            tableRestaurant.setUser(user);
            tableRestaurant = tableRestaurantRepository.save(tableRestaurant);

            TableRestaurantDTO tableDTO = new TableRestaurantDTO();
            tableRestaurantMapper.mapToRest(tableRestaurant, tableDTO);
            return tableDTO;
        } else {
            throw new IllegalArgumentException("Table or User not found");
        }
    }
    public TableRestaurantDTO getTableById(Long id) {
        TableRestaurant tableRestaurant = tableRestaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Table not found"));
        TableRestaurantDTO tableRestaurantDTO = new TableRestaurantDTO();
        tableRestaurantMapper.mapToRest(tableRestaurant, tableRestaurantDTO);
        return tableRestaurantDTO;
    }
    public TableRestaurantDTO updateTable(Long id, TableRestaurantDTO tableRestaurantDTO) {
        TableRestaurant tableRestaurant = tableRestaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Table not found"));
        tableRestaurantMapper.mapFromRest(tableRestaurantDTO, tableRestaurant);
        tableRestaurant = tableRestaurantRepository.save(tableRestaurant);
        TableRestaurantDTO updatedTableDTO = new TableRestaurantDTO();
        tableRestaurantMapper.mapToRest(tableRestaurant, updatedTableDTO);
        return updatedTableDTO;
    }
    public void deleteTable(Long id) {
        Optional<TableRestaurant> optionalTable = tableRestaurantRepository.findById(id);
        if (optionalTable.isPresent()) {
            tableRestaurantRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Table not found with ID: " + id);
        }
    }
    public List<TableRestaurantDTO> getAllTables() {
        List<TableRestaurant> tables = tableRestaurantRepository.findAll();
        return tables.stream()
                .map(table -> {
                    TableRestaurantDTO dto = new TableRestaurantDTO();
                    tableRestaurantMapper.mapToRest(table, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}