package ru.skillbranch.devintensive.models

/**
 * @author iamserj
 * 16.07.2019 22:34
 */

class Bender (var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion():String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return when(question) {
            Question.IDLE -> question.question to status.color
            else -> "${checkAnswer(answer)}\n${question.question}" to status.color
        }
    }

    private fun checkAnswer(answer: String): String {
        return if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился"
        } else {
            if (status == Status.CRITICAL) {
                resetStates()
                "Это неправильный ответ. Давай все по новой"
            } else {
                status = status.nextStatus()
                "Это неправильный ответ"
            }
        }
    }

    private fun resetStates() {
        status = Status.NORMAL
        question = Question.NAME
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus():Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }


    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            // firstOrNull() Returns the first character, or null if the char sequence is empty.
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isUpperCase() ?: false
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isLowerCase() ?: false
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            // \d Matches any digit character (0-9).
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("\\d")).not()
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            // ^ start of string; [0-9] match a single char in the list 0-9; * matches from 0 to unlimited times, as many times as possible; $ end of string
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]*$"))
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            // [0-9]{7} match a single char in the list 0-9 * seven times
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]{7}$"))
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
        };

        abstract fun nextQuestion():Question
        abstract fun validate(answer: String):Boolean
    }

}