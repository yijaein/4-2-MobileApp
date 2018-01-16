package yi.jaein.musicplayer

/**
 * Created by a303-1 on 2018. 1. 16..
 */
/*
    기본 타입 코틀린에서 모든 것은 객체이다
    모든것에 멤버 함수나 프로퍼티를 호출 가능하다는 의미에서
    자바에서는 프리미티브 레퍼런스로 나눠져 있지만 코틀린은 아니다

    -자바의 숫자형과 거의 비슷하게 처리
    -kotlin에서 number는 클래스임 java의 primitive type에 직접 접근 할 수 없음
    -java에서 숫자형이던 char 가 kotlin 에서는 숫자형이 아님
    -kotlin은 무조건 class로 접근한다
    -kotlin에서 8진수는 지원x
    -리터럴 타입은 거의 비슷하다
    -underscore
    val byte = 0b110110110_11011020210_121212121_1212121
    -> 숫자세기 좋음

    Representation
    -java 플래폼에서 숫자형은 JvM primitive type 으로 저장됨
    -nullable 이나 제너릭의 경우에는 박심됨
    -박싱된 경우 identitiy를 유지하지 않는다


 */