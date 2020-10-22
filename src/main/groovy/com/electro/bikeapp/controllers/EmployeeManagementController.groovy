package com.electro.bikeapp.controllers

import com.electro.bikeapp.dtos.AddEmployeeDTO
import com.electro.bikeapp.services.EmployeeService
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired

@RestController
@Slf4j
class EmployeeManagementController {

    @Autowired
    EmployeeService employeeService

    //TODO: Marie /manager/addEmployee
    /**
     * POST - create employee
     * @requestBody JSON user login credentials???
     * @param AddEmployeeDTO[]
     * @return void
     */
    @PostMapping(value = 'manager/addEmployee', produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void addEmployee(@RequestBody AddEmployeeDTO[] employeeParameters){
        log.info('Adding a new employee to the system')
        employeeService.addEmployee(employeeParameters)
    }

    //TODO: Marie /manager/updateEmployee
    /**
     * PATCH - update employee
     * @requestBody
     * @param AddEmployeeDTO[], String username
     * @return void
     */
    @PatchMapping(value = 'manager/updateEmployee/{username}', produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void updateEmployee(@RequestBody AddEmployeeDTO[] employeeParameters, @PathVariable String username){
        log.info('Updating an existing employee in the system')
        employeeService.updateEmployee(employeeParameters, username)
    }

    //TODO: Marie /manager/removeEmployee
    /**
     * DELETE - delete employee
     * @requestBody
     * @param AddEmployeeDTO[]
     * @return void
     */
    @DeleteMapping(value = 'manager/removeEmployee/{username}', produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)

    void deleteEmployee(@PathVariable String username){
        log.info('Deleting an employee from the system')
        employeeService.deleteEmployee(username)
    }
}

