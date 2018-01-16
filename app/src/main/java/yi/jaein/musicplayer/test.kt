package yi.jaein.musicplayer

/**
 * Created by a303-1 on 2018. 1. 16..
 */
fun test(){
    var a:Int =10000
    var b:Int? = 10000
    println("a===b:${a===b}")
    println("a===b:${a==b}")
}

/*
 Explicit Conversions
 -작은 타입은 큰 타입의 하위 타입이 아님 즉 작은 타입에서 큰 타입으로의 대입이 안딤
 val a:Int =1

 val b:Long =a.toLong()

 -명시적을 변환을 해줘야함
 val:Int = b.toInt()
 */
/*
     문자
     -char는 숫자로 취급 되지 않는다

     fun check(c:Char){
     if(c==1){} -> 에러


     }
     fun chekc(c:Char){
     if(c=='a'){} -> 출력값이 유니코드로 나온다
     }
 */
/*
    배열
    -배열은 Array클래스로 표현됨
    -get, set([ 연산자 오버로딩됨)
    -size 등 유용한 멤버 함수 포함

    var array:Array<String> = arrayOf("코틀린"강좌)
    println(array.get(0)
    prinntln(array[0])
    println(array.size)
 */