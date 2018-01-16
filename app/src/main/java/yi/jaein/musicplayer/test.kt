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

    특별한 Array 클래스
    -primitive 타입의 박싱 오버헤드를 없애긱 위한 배열
    -IntArray,ShortArray,IntArray
    -Array를 상송한 클래스들은 아니지만 Array와 같은 메소드와 프로퍼티를 가짐
    -size등 유용한 멤버 함수 포함
    val x: IntArray = intArrayOf(1,2,3)
    x[0] =7
    println(x.get(0))
    println(x[0])
    println(x.size)

    Kotlin 문자열
    -문자열은 String 클래스로 표현
    -String 은 characters로 구성됨
    -s[i]와 같은 방식으로 접근 가능

    var x:String ="Kotlin
    println(x.get(0)
    println(x[0])
    println(x.length)

    for(c in x){
    println(c)
    }


    -문자열 리터럴
    -escaped string ("Kotln")
    전통적인 방식으로 Java String 과 거의 비슷
    Backslash를 사용하여  excaping 처리

    -raw string(""Kotlin"")
    escaping 처리 필요 없음
    개행이나 어더한 문자 포함가능

 */