package com.cartrecipes.controller

import com.cartrecipes.controller.dto.error.ErrorResponse
import com.cartrecipes.exception.DuplicateRecipeException
import com.cartrecipes.exception.NotFoundException
import lombok.extern.log4j.Log4j2
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
@Log4j2
class RestExceptionHandler {

    companion object {
        private val log = LogManager.getLogger(RestExceptionHandler::class.java)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            ex.javaClass.simpleName,
            ex.message,
            HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse)
    }

    @ExceptionHandler(DuplicateRecipeException::class)
    fun handleDuplicateRecipeException(ex: DuplicateRecipeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            ex.javaClass.getSimpleName(),
            ex.message,
            HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            ex.body.title,
            ex.body.detail,
            HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            ex.javaClass.simpleName,
            ex.message,
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
        log.error("An unexpected error occurred: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse)
    }
}