package yi.jaein.musicplayer

/**
 * Created by Jan on 2018. 1. 16..
 */
/*
    void ,, unit 리턴
    val: 읽기 전용 변수
    val a Int = 1 ? 스위프트랑 비슷한데 ?

    var x=5  : tutable  변수
     /*
      주석 중첩 가능
      */
      fun maxOf(a:Int, b :Int):Int{
      if(a>b){
      return a
      {else{
      return b
      }
      }

      fun parseInt(str:String): Int?{
      값이 null일 수 있는 경우 타입에 nullable 마크를 명시 해야함
      }

       코틀린은 타입을 자동으로 변환이 가능하다

       fun getStringLength(obj:Any):Int{
       if(obj is String){
       return obj.length
       }
       return null
       }

        /*
           자바 switch case(정수 인테저) 와 다르게  다른 타입도 지원하며 더 강력하다
         */

       when expression
       fun describe(obj:Any)String =
       when (obj){

       1-> "one"
       "hello" -> "Greeting"
        is Long -> "Not a String"
        !String -> "Not a String"
        else -> "Unknown"
        }

        /*
        ranges

        -In 연산자를 이용해서 숫자 범위를 체크할 수 있다
        val x=3
        if(x in 1...10){
        println("fits in range")
        }

        3이 1과 10 사이에 있는 체크




        -range 를 이용한 for loop
        for (x in 1..5){
        print(x)
        }



         */
         * /*
         * collections
         * val items = lisfOf("apple", "banana" , "Kiwi")
         * for(item in items){
         * println(item)
         *
         * }
         * 컬렉션에 있는지 확인할 수 있다 .
         * 람다식을 이용해서 컬렉션에 filter,map 등의 연산 가능
         * -> kotlin은 람다식 사용 쉽다
         *
         * */
         *

 */