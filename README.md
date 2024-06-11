# WebFlux

### Reactive System
1. Responsive (응답성)
2. Resilient (회복성, 시스템 장애 -> 응답성 유지)
3. Elastic (탄력성, 작업량의 다양한 변화 -> 응답성 유지)
4. Message Driven (메시지 기반 동작)

### Reacitve Programming
1. 데이터 소스에 변경이 있을 때마다 데이터를 전파
2. 선언형 프로그래밍 패러다임 -> 실행할 동작을 구체적으로 명시하지 않고 목표만 정함
3. 함수형 프로그래밍 기법 사용   
> 명령형 프로그래밍 예시
```java
List<Integer> numbers = Arrays.asList(...);
int sum = 0;
for(...) {
  if(...) {
    sum += number;
  }
}
```
> 선언형 프로그래밍 예시   
```java
List<Integer> numbers = Arrays.asList(...);
int sum = numbers.stream()
  .filter( )
  .mapToint(number -> number)
  .sum();
```

### Reactive Streams
리액티브 프로그래밍을 표준화한 명세
> 4가지 인터페이스 정의
> 1. Publisher
>    : 데이터를 통제하는 주체
> 2. Subscriber
>    : 퍼블리셔에서 통제한 데이터를 구독하는 구독자
> 3. Subscription
>    : 구독 자체를 정의해놓은 인터페이스
> 4. Processor
>    : Publisher와 Subscriber 역할을 동시에 할 수 있는 인터페이스eactive System
1. Responsive (응답성)
2. Resilient (회복성, 시스템 장애 -> 응답성 유지)
3. Elastic (탄력성, 작업량의 다양한 변화 -> 응답성 유지)
4. Message Driven (메시지 기반 동작)

### Reacitve Programming
1. 데이터 소스에 변경이 있을 때마다 데이터를 전파
2. 선언형 프로그래밍 패러다임 -> 실행할 동작을 구체적으로 명시하지 않고 목표만 정함
3. 함수형 프로그래밍 기법 사용   
> 명령형 프로그래밍 예시
```java
List<Integer> numbers = Arrays.asList(...);
int sum = 0;
for(...) {
  if(...) {
    sum += number;
  }
}
```
> 선언형 프로그래밍 예시   
```java
List<Integer> numbers = Arrays.asList(...);
int sum = numbers.stream()
  .filter( )
  .mapToint(number -> number)
  .sum();
```

### Reactive Streams 
리액티브 프로그래밍을 표준화한 명세
> [4가지 인터페이스 정의](https://github.com/reactive-streams/reactive-streams-jvm?tab=readme-ov-file#specification)
> 1. Publisher
>    : 데이터를 통제하는 주체
> 2. Subscriber
>    : 퍼블리셔에서 통제한 데이터를 구독하는 구독자
> 3. Subscription
>    : 구독 자체를 정의해놓은 인터페이스
> 4. Processor
>    : Publisher와 Subscriber 역할을 동시에 할 수 있는 인터페이스
>    
Reactive Streams를 구현한 구현체
> RxJava   
> Java 9 Flow API   
> Akka Streams   
> Reactor   
> 그 외 RxJs, RxScala, Rx Android 등의 리액티브 확장
>
> 
