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
> [4가지 인터페이스 정의](https://github.com/reactive-streams/reactive-streams-jvm?tab=readme-ov-file#specification)
> 1. Publisher
>    : 데이터를 통제하는 주체
> 2. Subscriber
>    : 퍼블리셔에서 통제한 데이터를 구독하는 구독자
> 3. Subscription
>    : 구독 자체를 정의해놓은 인터페이스
> 4. Processor
>    : Publisher와 Subscriber 역할을 동시에 할 수 있는 인터페이스

Reactive Streams를 구현한 구현체
> RxJava   
> Java 9 Flow API   
> Akka Streams   
> Reactor   
> 그 외 RxJs, RxScala, Rx Android 등의 리액티브 확장

-------------------------------------------------------
## Blocking I/O 방식
![photo_2024-06-11_13-31-14](https://github.com/bckkingkkang/WebFlux/assets/131218470/e9fca3a4-955e-43ed-af5f-d629952be13c)
> * 작업 쓰레드(지점 API)가 종료될 때까지 요청을 한 쓰레드(본사 API)는 차단된다.   
> * 이러한 단점 보완 -> 멀티 쓰레딩 기법을 통해 추가 쓰레드 할당 가능   
> * CPU 대비 많은 수의 쓰레드를 사용하는 애플리케이션은 비효율적   
>> * 컨텍스트 스위칭(Context Switching)으로 인한 쓰레드 전환 비용 발생   
>> * 메모리 사용에 있어서 오버헤드 발생   
>> * 쓰레드 풀에서의 응답 지연 문제 발생     
>>> `서블릿 기반의 애플리케이션이 쓰레드를 사용하고 사용이 끝난 쓰레드를 다시 쓰레드 풀에 반납하는 과정에서 응답이 지연된다.`   
>>> * 쓰레드 풀에 아직 사용되지 않은 유휴 쓰레드가 없을 경우 응답 지연 시간이 길어진다.   
>>> * 쓰레드가 반납된다 하더라도 반납된 쓰레드가 사용 가능하도록 전환되는 과정에서 응답 지연이 발생한다.   

### Context Switching   
여러 개의 프로세스들이 번갈아 가면서 실행되는 과정   
![photo_2024-06-11_13-31-16](https://github.com/bckkingkkang/WebFlux/assets/131218470/81f19d4c-c185-4931-817d-b795dc6dae40)
* 실행 시점과 Idle Time의 종료 시점 불일치
```
* PCB에 저장하고 PCB에서 로드하는데 추가 시간이 들어가기 때문에 실행 시점과 유휴 시점이 딱 들어맞지 않는다.
* PCB에 저장하고 PCB에서 로드하는 시간동안 CPU는 대기하기 때문에 일을 할 수 없다.
* 컨텍스트 스위칭이 잦을수록 전체 대기 시간이 길어지므로 성능이 저하된다.
```

하나의 프로세스에 여러 개의 쓰레드(프로세스의 자식 개념)가 번갈아 가면서 실행   
프로세스와 마찬가지로 쓰레드 같은 경우에도 번갈아 가면서 실행이 되기 때문에 컨텍스트의 스위치 비용이 발생한다.

## Non-Blocking I/O 방식
![photo_2024-06-11_13-31-14](https://github.com/bckkingkkang/WebFlux/assets/131218470/643f0aec-7c7a-4592-bb1e-486270d22553)
* 작업 쓰레드의 종료와 상관없이 요청을 한 쓰레드는 차단되지 않는다.
* 적은 수의 쓰레드를 사용하기 때문에 쓰레드 전환 비용이 적게 발생한다.
* 따라서 CPU 대기 시간 및 사용량에 있어서도 효율적이다.
* CPU를 많이 사용하는 작업이 포함되어 있을 경우에는 성능 향상에 악영향을 준다.
* 사용자의 요청에서 응답까지의 과정에 Blocking I/O 요소가 포함되어 있을 경우 Non-Blocking의 이점을 발휘하기 힘들다.
