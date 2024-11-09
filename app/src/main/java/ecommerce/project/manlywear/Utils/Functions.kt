package ecommerce.project.manlywear.Utils


/**
 * 1.Valid Cases
 * Simple Valid Email: "user@example.com"
 * Email with Numbers and Special Characters: "user123+test@example.com"
 * Email with Dashes and Periods in Domain: "user.name@example.co.uk"
 * Email with Subdomains: "user@mail.server.example.com"
 * Email with Underscore: "first_last@example.com"
 *
 * 2.Invalid Cases
 * Missing @ Symbol: "userexample.com"
 * Missing Username: "@example.com"
 * Consecutive Dots: "user..name@example.com"
 * Special Characters Not Allowed: "user!@example.com"
 * Spaces in Email: "user name@example.com"
 * Missing Domain: "username@"
 */
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    return email.matches(emailRegex.toRegex())
}