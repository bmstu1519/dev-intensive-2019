package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when(question){

        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }
    fun listenAnswer(answer:String ) : Pair<String, Triple<Int, Int, Int>>{

        return if(question == Question.IDLE) {
            question = question.nextQuestion()
            question.question to status.color
        }else
            when(question.answer.contains(answer)){
                true -> {
                    question = question.nextQuestion()
                    "Отлично - ты справился\n" + question.question to status.color
                }
                false -> when(question.validateQuestion(answer).first){
                            true -> question.validateQuestion(answer).second+"\n" +
                                    question.question to status.color
                            false -> if(this.status == Status.CRITICAL){
                                status = Status.NORMAL
                                question = Question.NAME
                                "Это неправильный ответ. Давай все по новой" +
                                        "\n${question.question}" to status.color
                                } else{
                                    status = status.nextStatus()
                                    "Это неправильный ответ\n${question.question}" to status.color
                                }
            }
        }
    }
    enum class Status(val color : Triple<Int,Int,Int>){
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus(): Status{
            return if (this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            }else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>){
        NAME("Как меня зовут?", listOf("Бендер","Bender")) {

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return when(answer.contains(userAnswer.capitalize())) {
                    true -> Pair(true, "Имя должно начинаться с заглавной буквы")
                    false -> Pair(false, "")
                }
            }
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")){

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return when(answer.contains(userAnswer.toLowerCase())) {
                    true -> Pair(true, "Профессия должна начинаться со строчной буквы")
                    false -> Pair(false, "")
                }
            }
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл","дерево","metal","iron","wood")){

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return when(answer.contains(userAnswer.filter { it.isLetter() })) {
                    true -> Pair(true, "Материал не должен содержать цифр")
                    false -> Pair(false, "")
                }
            }
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return when(answer.contains(userAnswer.filter { it.isDigit() })) {
                    true -> Pair(true, "Год моего рождения должен содержать только цифры")
                    false -> Pair(false, "")
                }
            }
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return when(!userAnswer.isDigitsOnly() || userAnswer.length != 7) {
                    true -> Pair(true, "Серийный номер содержит только цифры, и их 7")
                    false -> Pair(false, "")
                }
            }
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){

            override fun validateQuestion(userAnswer: String): Pair<Boolean,String> {
                return Pair(true, "")
            }
            override fun nextQuestion(): Question = NAME

        };

        abstract fun nextQuestion(): Question
        abstract fun validateQuestion(userAnswer : String): Pair<Boolean, String>
    }
}