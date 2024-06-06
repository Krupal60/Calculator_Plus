package com.plus.calculatorplus.presentation.validation

fun bmiValidation(
     age: String,
     cm: String,
     weight: String,
): Pair<Boolean, String> {
    return when {
        age.isEmpty() -> {
            Pair(false, "Enter Age")
        }

        cm.isEmpty() -> {
            Pair(false, "Enter cm or ft")
        }

        weight.isEmpty() -> {
            Pair(false, "Enter weight")
        }

        else -> {
            // Perform individual validations and short-circuit if any fails
            val ageValidation = ageValidate(age)
            if (!ageValidation.first) return ageValidation

            val cmValidation = cmValidation(cm)
            if (!cmValidation.first) return cmValidation

            val yearsResult = weightValidation(weight)
            if (!yearsResult.first) return yearsResult

            // All validations passed
            Pair(true, "")
        }
    }
}

fun weightValidation(
    weight: String
): Pair<Boolean, String> {
    return when {
        weight.isEmpty() -> {
            Pair(false, "Enter Investment years")
        }

        else -> {
            val weightInt = weight.toInt()
            if (weightInt in 2..500 ) {
                Pair(true, "")
            } else {
                Pair(false, "Weight need in 2 to 500 Kg range")
            }

        }
    }
}


fun ageValidate(
    age: String
): Pair<Boolean, String> {
    return when {
        age.isEmpty() -> {
            Pair(false, "Enter Age")
        }

        else -> {
            val ageInt = age.toInt()
            if (ageInt in 18..120) {
                Pair(true, "")
            } else {
                Pair(false, "Age need in range 18 to 120 range")
            }

        }
    }
}

fun cmValidation(
    cm: String
): Pair<Boolean, String> {
    return when {
         cm.isEmpty() -> {
            Pair(false, "Enter Cm or Ft")
        }

        else -> {
            val cmInt = cm.toInt()
            if (cmInt in 50..280) {
                Pair(true, "")
            } else {
                Pair(false, "Cm or ft need to be valid")
            }
        }

    }
}