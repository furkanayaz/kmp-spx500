package org.ayaz.bookstore.presentation.util.validations.annotations

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import jakarta.validation.constraints.Size
import java.util.regex.Pattern
import kotlin.reflect.KClass

private const val MESSAGE = "Your password must contain at least one uppercase letter and one special character."

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention
@Size(min = 8, max = 20)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class Password(
    val message: String = MESSAGE,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class PasswordValidator: ConstraintValidator<Password, String> {
    private companion object {
        const val REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+\$"
    }

    override fun isValid(
        value: String,
        context: ConstraintValidatorContext?
    ): Boolean = Pattern.matches(REGEXP, value)
}