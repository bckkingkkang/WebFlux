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
![photo_2024-06-11_14-09-49](https://github.com/bckkingkkang/WebFlux/assets/131218470/9e72a4a7-37fa-4a3d-a0b4-03ce1dd6bbfc)
* 작업 쓰레드의 종료와 상관없이 요청을 한 쓰레드는 차단되지 않는다.
* 적은 수의 쓰레드를 사용하기 때문에 쓰레드 전환 비용이 적게 발생한다.
* 따라서 CPU 대기 시간 및 사용량에 있어서도 효율적이다.
* CPU를 많이 사용하는 작업이 포함되어 있을 경우에는 성능 향상에 악영향을 준다.
* 사용자의 요청에서 응답까지의 과정에 Blocking I/O 요소가 포함되어 있을 경우 Non-Blocking의 이점을 발휘하기 힘들다.

----------------------------------------------------------
### Spring MVC, Spring WebFlux
|spring MVC|Spring WebFlux|
|:-------------------------:|:-----------------------------:|
| Blocking I/O 방식 | Non-Blocking I/O 방식 |
| 요청 당 하나의 Thread 사용 (OneRequest OneThread 모델) | 하나의 Thread로 대량의 요청 처리 가능 |
| 과도한 Thread 사용으로 인한 CPU 대기 시간이 늘어나고 메모리 사용에서 오버헤드가 발생한다. | 적은 수의 Thread를 사용하므로 CPU와 메모리를 효율적으로 사용한다. |
| 10년 이상 주도적으로 사용된 명령형 프로그래밍 기반 기술 | CPU를 많이 사용하는 복잡한 계산을 요청할 경우 다른 요청들을 처리할 수 없다. |
| | 요청에서 응답까지 Fully Non-Blocking이어야 진정한 효과를 발휘한다. |
|  | 선언형 프로그래밍 |

## Spring WebFlux
* Spring 5부터 지원하는 리액티브 웹 프레임워크
* 비동기(병렬적 처리) Non-Blocking I/O(입출력) 방식으로 적은 수의 Thread를 사용한다.
* Reactive Streams의 구현체 중 하나인 Reactor에 의존하여 비동기 로직을 구성하고 리액티브 스트림을 제공한다.
* Reactor 기반이지만 RxJava 등 다른 리액티브 확장 라이브러리를 쉽게 적용할 수 있다.

![photo_2024-06-11_15-16-43](https://github.com/bckkingkkang/WebFlux/assets/131218470/16438da3-da82-4887-80fb-3bdf006be080)

* Event Loop 방식을 통한 Spring WebFlux의 Non-Blocking Process

![photo_2024-06-11_15-17-52](https://github.com/bckkingkkang/WebFlux/assets/131218470/240899f8-76a7-4946-a41c-f0d620aaaa31)

* Spring WebFlux를 사용하기 적합한 시스템   
> * Blocking I/O 방식으로 처리하는데 한계가 있는 대량의 요청 트래픽이 발생하는 시스템   
> * 마이크로 서비스 기반 시스템 `많은 수의 I/O 발생`   
> * 스트리밍 시스템 또는 실시간 시스템
> * 네트워크 접속이 느린 클라이언트의 요청 처리

-----------------------------------------------------------------------------
## Reactor
- 리액티브 프로그래밍을 위한 리액티브 라이브러리
- Reactive Streams 스펙을 구현한 구현체 중 하나이다.
- Spring 에코 시스템에서 Reactive Stack의 기반이 되며 Spring WebFlux 프레임워크에 포함되어 있다.

### [Reactor 특징](https://projectreactor.io/)
1. Fully Non-Blocking
   > 클라이언트의 요청을 시작으로 데이터 액세스 레이어를 거쳐서 다시 클라이언트의 Response를 보낼 때까지 Blocking I/O가 전혀 개입하지 않는다는 의미
2. Functional API(함수형 API)
   > Functional API를 사용해서 Publisher와 Subsriber 간에 상호작용을 한다.   
   > reactive 프로그래밍의 특징 : Functional API 사용
3. Sequences 타입
   > Reactor에서 데이터를 생산해서 내보내는 퍼블리셔 타입 2가지
   >> * Mono : 데이터를 0 or 1 건 내보낼 수 있다.
   >> * Flux : n개 이상의 데이터를 내보낼 수 있다.
4. Non-Blocking 애플리케이션(대량의 Request) 제작에 특화
   > 마이크로 서비스에 적합한 라이브러리
5. backpressure 지원

### Reactor 용어 정의
* **Publisher** : 발행자, 게시자, 생산자, 방출자 (Emitter)
* **Subscriber** : 구독자, 소비자
* **[Emit](https://en.dict.naver.com/#/entry/enko/f6136f8695084091adeb3b60249af579)** : Publisher가 데이터를 내보내는 것
* **Sequence** : Publisher가 emit하는 데이터의 연속적인 흐름을 정의해놓은 것으로 Operator(연산자) 체인 형태로 정의된다.
  > Flux, just, map 같은 연산자 체인으로 구성된 코드
* **subscribe** : Publisher를 통해서 정의된 Sequence를 Subscriber가 구독하는 것
* **Dispose** : Subscriber가 Sequence 구독을 해지하는 것

![photo_2024-06-12_11-36-34](https://github.com/bckkingkkang/WebFlux/assets/131218470/bef71a06-3f0b-46a4-bb45-ebfe9d7d3fa6)   

### Reactive Programming의 흐름
1. 퍼블리셔가 데이터를 **생성**
2. Operator(ex.map)를 사용해서 데이터를 **가공**
3. 최종적으로 가공한 데이터를 Subscriber에 **전달**













-------------------------------------------------------------------------------------------------
참고   
[Spring MVC vs Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux/new-framework.html)     
[Reactive Microservices With Spring Boot](https://spring.io/reactive)

