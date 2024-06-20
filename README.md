# WebFlux
## 개요
* **[검색, 설치 순서 정리](#검색-설치-순서-정리)**
* **[개념 정리](#개념-정리)**
-------------------------------------------------------------
## 검색, 설치 순서 정리

### mongoDB 다운로드
<details>
    <summary>1. mongoDB download</summary>   

  ![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/6b24924a-2701-429b-868c-9448984f6b86)

</details>
   
<details>
    <summary>2. 시스템 환경 변수 편집</summary>
  
  ![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/29abafc2-4429-4d20-a543-3f8c1837a48b)

</details>

<details>
    <summary>3. mongoDB 버전 확인</summary>
    
![화면 캡처 2024-06-19 112551](https://github.com/bckkingkkang/WebFlux/assets/131218470/3aba57a3-5739-424c-a7c8-8836df1a51d1)

</details>

<details>
    <summary>4. mongoDB compass</summary>
  
![화면 캡처 2024-06-20 143840](https://github.com/bckkingkkang/WebFlux/assets/131218470/6a63a9ab-1abb-48f4-92cc-d2ae30ce7bf5)

![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/ea7a0449-2538-41e6-84b9-ba90dc77f3d0)

</details>

<details>
    <summary>5. ADD DATA</summary>
    
![화면 캡처 2024-06-20 143904](https://github.com/bckkingkkang/WebFlux/assets/131218470/d312799c-b60b-4744-894c-8fd23bd3b02a)
![화면 캡처 2024-06-20 144525](https://github.com/bckkingkkang/WebFlux/assets/131218470/7d91433f-40de-4f07-9315-84e121308e0c)
![화면 캡처 2024-06-20 144619](https://github.com/bckkingkkang/WebFlux/assets/131218470/a6f9cb2c-67c3-4ffb-9352-0fb7177aeafe)

</details>

<details>
    <summary>6. application.properties 설정</summary>
    
```properties
# 접속할 DB IP 주소
spring.data.mongodb.host=localhost
# 접속할 DB port 번호
spring.data.mongodb.prot=27017
# 접속할 계정이 위치한 DB 이름
spring.data.mongodb.authentication-database=kahyun_webflux
spring.data.mongodb.uri=mongodb://localhost:27017/kahyun_webflux
```   
</details>

<details>
    <summary>7. Vo </summary>

```java
@Data
@Document(collection = "board")
public class BoardVO {
    @Id
    private String id;
    private String seq;
    private String title;
    private String content;
    private String authorId;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;
}
```   
</details>





-------------------------------------------------------------
## 개념 정리
- **Reactive**
  - [Reactive System](#reactive-system)
  - [Reactive Programming](#reacitve-programming)
  - [Reactive Streams](#reactive-streams)
- **[Blocking I/O](#blocking-io-방식)**
  - [Context Switching](#context-switching)
- **[Non-Blocking I/O](#non-blocking-io-방식)**
- **[Spring MVC, Spring WebFlux](#spring-mvc-spring-webflux)**
  - [Spring WebFlux](#spring-webflux)
- **[Reactor](#reactor)**
  - [Reactor 특징](#reactor-특징)
  - [Reactor 용어 정의](#reactor-용어-정의)
  - [Reactive Programming의 흐름](#reactive-programming의-흐름)
- **[Marble Diagram](#marble-diagram)**
- **Publisher**
  - [Mono](#mono)
  - [Flux](#flux)
- **Sequence**
  - [Cold Sequence](#cold-sequence)
  - [Hot Sequence](#hot-sequence)
- **[Backpressure](#backpressure)**
  - [Publisher와 Subscriber 간의 프로세스](#publisher와-subscriber-간의-프로세스)
  - [Reactor에서의 BackPressure 처리 방법](#reactor에서의-backperssure-처리-방법)
  - [BackPressure 전략](#backpressure-전략)
- **[Sinks](#sinks)**
- **[Scheduler](#scheduler)**
  - [ParallelFlux의 동작 방식](#parallelflux의-동작-방식)
    - [1. parallel()만 사용한 경우](#1-parallel-만-사용한-경우)
    - [2. runOn()까지 사용한 경우](#2-runon까지-사용한-경우)
    - [3. CPU 코어 갯수 지정](#3-cpu-코어-갯수-지정)
  - [publishOn()과 subscribeOn()의 동작 이해 1](#publishon과-subscribeon의-동작-이해-1)
  - [publishOn()과 subscribeOn()의 동작 이해 2](#publishon과-subscribeon의-동작-이해-2)
  - [publishOn()과 subscribeOn()의 동작 이해 3](#publishon과-subscribeon의-동작-이해-3)
  - [publishOn()과 subscribeOn()의 동작 이해 4](#publishon과-subscribeon의-동작-이해-4)
  - [publishOn()과 subscribeOn()의 동작 이해 5](#publishon과-subscribeon의-동작-이해-5)
  - [publishOn()과 subscribeOn()의 동작 이해 6](#publishon과-subscribeon의-동작-이해-6)
  - [Scheduler의 종류](#scheduler의-종류)

---------------------------------------------------------------------------------------
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

![화면 캡처 2024-06-11 145028](https://github.com/bckkingkkang/WebFlux/assets/131218470/2a0b13ac-f119-4c88-a5c9-f8575793e981)

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
1. [Fully Non-Blocking](#non-blocking-io-방식)
   > 클라이언트의 요청을 시작으로 데이터 액세스 레이어를 거쳐서 다시 클라이언트의 Response를 보낼 때까지 Blocking I/O가 전혀 개입하지 않는다는 의미
2. Functional API(함수형 API)
   > Functional API를 사용해서 Publisher와 Subsriber 간에 상호작용을 한다.   
   > reactive 프로그래밍의 특징 : Functional API 사용
3. Sequences 타입
   > Reactor에서 데이터를 생산해서 내보내는 퍼블리셔 타입 2가지
   >> * [Mono](#mono) : 데이터를 0 or 1 건 내보낼 수 있다.
   >> * [Flux](#flux) : n개 이상의 데이터를 내보낼 수 있다.
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

----------------------------------------------------------------------------------
### Marble Diagram
: 리액티브 프로그래밍에서 데이터 처리 흐름을 그림으로 시각화해놓은 다이어그램

## Mono
* 0개 또는 1개의 데이터를 emit하는 Publisher (Compare with RxJava Maybe)
* 데이터 emit 과정에서 에러가 발생하면 onError signal을 emit한다.

![photo_2024-06-12_13-20-31](https://github.com/bckkingkkang/WebFlux/assets/131218470/68ba274f-8345-4bda-ae2c-4bb5811d2617)

## Flux
* 0 ~ N개의 데이터를 emit하는 Publisher
* 데이터 emit 과정에서 에러가 발생하면 onError signal을 emit한다.

![photo_2024-06-12_13-20-29](https://github.com/bckkingkkang/WebFlux/assets/131218470/64d815bb-9bee-4df4-855c-95161f5c6752)

<details>
    <summary>Concat / concatWith</summary>   

**ConcatWith** : 앞 쪽에 있는 Publisher와 ConcatWith의 parameter로 입력된 Publisher를 연결
```java
@Slf4j
public class FluxExample3 {
    public static void main(String[] args) {
        Flux<Object> flux =
                // just operator의 경우 null값을 포함할 수 없지만 justOrEmpty의 경우 null 값 포함 가능
                Mono.justOrEmpty(null)
                        .concatWith(Mono.justOrEmpty("Jobs"));
        // ConcatWith 를 이용해서 Mono 와 Mono 를 결합해 새로운 Flux 를 생성할 수 있다.
        
       flux.subscribe(data->log.info("# result : {}", data));
    }
}
```   
**concat** : concat operator의 parameter로 입력된 Publisher 들을 연결
```java
@Slf4j
public class FluxExample4 {
    public static void main(String[] args) {
        Flux.concat(
                Mono.just("Venus"),
                Flux.just("Earth"),
                Flux.just("Moon", "Sun"),
                Flux.just("Mars"))
                // 하나의 리스트 안에 각각의 데이터들이 담겨 리스트로 전달
                .collectList()
                .subscribe(planetList -> log.info("# Solar System : {}", planetList)
        );
    }
}
```
</details>

----------------------------------------------------------------------
## Cold Sequence
* subscriber의 구독 시점이 달라도 구독을 할 때마다 Publisher가 데이터를 emit하는 과정을 처음부터 다시 시작하는 데이터의 흐름   

![photo_2024-06-12_15-02-17](https://github.com/bckkingkkang/WebFlux/assets/131218470/9b6ea99a-9be2-46ac-85b4-96f833413242)   
* Subscriber가 구독을 할 때마다 타임라인에 처음부터 emit된 모든 데이터를 받을 수 있다.
* 시퀀스 타임라인이 구독을 할 때마다 cld sequence가 하나씩 더 생성
```java
@Slf4j
public class ColdSequenceExample {
    public static void main(String[] args) {

        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
                .map(String::toLowerCase);

        coldFlux.subscribe(country->log.info("# subscriber1 : {}", country));
        log.info("-------------------------------");
        coldFlux.subscribe(country->log.info("# subscriber2 : {}", country));
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/5a86994f-c8fb-4af4-ac71-d7e507a7090d)

## Hot Sequence
* Subscriber가 구독한 시점의 타임라인부터 emit된 데이터를 받을 수 있다.

![photo_2024-06-12_15-02-15](https://github.com/bckkingkkang/WebFlux/assets/131218470/a5224ea1-fd51-45ae-844d-4fa474bb2aab)
* Publisher가 데이터를 emit하는 과정이 한 번만 일어나고 Subscriber가 각 구독 시점 이후에 emit된 데이터만 전달받는다.
* 구독이 여러 번 발생해도 타임라인은 하나만 생성
```java
@Slf4j
public class HotSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("A","B","C","D","E"))
                        .delayElements(Duration.ofSeconds(1)).share();
        // share -> 원본 Flux를 여러 subscriber가 공유하도록 한다. (cold sequence를 hot sequence로 변환해주는 operator)

        concertFlux.subscribe(code -> log.info("# Subscriber {}", code));

        TimeUnit.SECONDS.sleep(3);

        concertFlux.subscribe(code -> log.info("# Subscriber 2 {}", code));

        TimeUnit.SECONDS.sleep(4);
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/4b867798-161b-4436-bb6d-f381fe63d75c)   

---------------------------------------------------------------------------------
## Backpressure
Publisher에서 emit되는 데이터들을 Subscriber 쪽에서 안정적으로 처리하기 위한 제어 기능   
![photo_2024-06-13_13-30-11](https://github.com/bckkingkkang/WebFlux/assets/131218470/f5248f89-1297-4534-85cb-aa08ae40a4fd)

### Publisher와 Subscriber 간의 프로세스

<details>
    <summary>publisher-subscriber 간 프로세스</summary>   

![photo_2024-06-13_13-30-14](https://github.com/bckkingkkang/WebFlux/assets/131218470/76978dcd-8249-45b3-8070-df72edae541d)
</details>

### Reactor에서의 BackPerssure 처리 방법
1. 요청 데이터의 개수를 제어하는 방법   
   - Subscriber가 적절히 제어할 수 있는 수준의 데이터 개수를 Publisher에게 요청   
2. Backpressure 전략을 사용하는 방법   
   - Reactor에서 제공하는 Backpressure 전략을 사용

### Backpressure 전략
1. **IGNORE**
   - Backpressure를 사용하지 않는다.
2. **ERROR**
   - Downstream으로 전달할 데이터가 Buffer에 가득 찬 경우, Exception을 발생
3. **DROP**
   - Downstream으로 전달할 데이터가 Buffer에 가득 찰 경우, Buffer 밖에서 대기하는 먼저 emit된 데이터부터 DROP한다.
   - BUFFER가 비워질 때까지 하나씩 DROP
   - Buffer가 가득 찬 상태에서 데이터가 들어온 경우, 그 즉시 DROP
     <details>
     <summary>자세히 보기</summary>   
     
     <img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/d83d100c-9565-4462-8359-56b40bcb8661" width="500"/>
   </details>   
4. **LATEST**
   - Downstream으로 전달할 데이터가 Buffer에 가득 찰 경우, Buffer 밖에서 대기하는 가장 최근(나중)에 emit된 데이터부터 Buffer에 채운다.
   - Buffer가 가득 찬 상태에서 데이터가 들어온 경우 즉시 Drop 되지 않고 데이터 하나가 더 들어온 경우 폐기된다. (최신 데이터가 아닌 데이터가 폐기)
     <details>
     <summary>자세히 보기</summary>  
  
     <img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/d2ab063d-a282-4e7e-9947-be2f1f162bbc" width="500"/>   

   </details>   
5. **[BUFFER](#buffer) 전략**
   - Downstream으로 전달할 데이터가 Buffer에 가득 찰 경우, Buffer 안에 있는 데이터를 DROP 시킨다.
     <details>
     <summary>BUFFER DROP-LATEST</summary>  
     
     <img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/9cca8741-303b-48d4-a7aa-f0aad878d663" width="500"/>

     </details>  
     <details>
     <summary>BUFFER DROP-OLDEST</summary>  
     
     <img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/d4c9497b-ea53-426c-88bc-4c69d9aa4d2b" width="500"/>

     </details>  

-----------------------------------------------------------
## Sinks
* Reactive Streams에서 발생하는 signal을 프로그래밍적으로 push 할 수 있는 기능을 가지고 있는 Publisher의 일종
* Thread-safe 가 보장되지 않을 수 있는 Processor 보다 더 나은 대안이 된다.
  > `Thread-Safe` : 멀티스레드 환경에서 함수나 변수 같은 공유 자원에 동시 접근할 경우에도 프로그램의 실행에 문제가 없다.   
  > `Thread-Safe가 보장되지 않는다` : 여러 개의 스레드가 공유 변수에 동시 접근해서 올바르지 않은 값이 할당된다거나, 공유 함수에 동시에 접근할 때 교착상태에 빠지게 되는 것
  >    
  > *Processor의 경우 onNext, onComplete, onError 메소드를 직접적으로 호출하기 때문에 Thread-Safe 하지 않을 수 있다.
  Sinks의 경우에는 동시 접근을 감지하고 동시접근을 하는 Thread 중에서 하나가 빠르게 실패하기 때문에 Thread-Safe가 보장된다.*
- Sinks는 Thread-Safe 하게 signal을 발생시킨다.
- Sinks는 Sinsk.Many 또는 Sinks.Oneinterface를 사용해서 Thread-safe하게 signal을 발생시킨다.

---------------------------------------------------------------
## Scheduler
* 구독 시점에 데이터가 emit되는 영역과 emit된 데이터를 operator로 가공 처리하는 영역을 분리해서 손쉽게 멀티쓰레딩을 가능하게 한다.
> `구성`   
> - operator 체인에서 Scheduler를 전환하는 역할을 하는 전용 operator   
> - Scheduler를 통해 생성되는 쓰레드 실행 모델을 지정하는 부분

* Scheduler를 위한 전용 Operator
- publishOn() : Operator 체인에서 Downstream Operator의 실행을 위한 쓰레드를 지정한다.
- subscribeOn() : 최상위 Upstream Publisher의 실행을 위한 쓰레드를 지정한다. 즉, 원본 데이터 소스를 emit 하기 위한 스케줄러를 지정한다.
- parallel() : Downstream에 대한 데이터 처리를 병렬로 분할 처리하기 위한 쓰레드를 지정한다.   
   
### parallelFlux의 동작 방식
![photo_2024-06-17_15-30-04](https://github.com/bckkingkkang/WebFlux/assets/131218470/f71e5f27-b18d-4d11-a71f-8a342a158ccf)    
> **②** : parallel operator가 return 값으로 반환하는 parallelFlux라는 특별한 타입에서 지원하는 runOn operator를 사용해서 Scheduler를 지정하게 되면 이 시점에 병렬 작업을 시작하게 됨   
> **③** : rail이라는 논리적인 작업 단위에서 분할되는 워크로드 처리

 
#### 1. parallel() 만 사용한 경우 
```java
@Slf4j
public class ParallelExample01 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15})
                .parallel()     // 병렬로 처리하겠다는 정의
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/846f9e41-c681-424b-9f77-995f3ee29bee)   
> 병렬로 처리되지 않고 메인 쓰레드에서 전부 처리가 됨   
> parallel 만 사용할 경우에는 병렬로 작업을 수행하지 않는다.      

   
#### 2. runOn()까지 사용한 경우
```java
@Slf4j
public class ParallelExample03 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 29})
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("{}", data));

        Thread.sleep(100L);
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/92344a26-212d-4dd8-89b9-bee86aafc9ee)   
> runOn() 을 사용해서 Scheduler 를 할당해주어야 병렬로 작업을 수행한다.   
> [CPU 코어 갯수](#cpu-코어-갯수) 내에서 worker thread 를 할당한다.

    
#### 3. CPU 코어 갯수 지정
```java
@Slf4j
public class ParallelExample04 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel(4)        // Thread 의 갯수 지정
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("{}", data));

        Thread.sleep(100L);
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/e64f5166-6f67-402e-b26b-0c6fa21bbab6)    
> CPU 코어 갯수에 의존하지 않고, worker thread 를 강제 할당한다.   

### publishOn()과 subscribeOn()의 동작 이해 1
- Operator 체인에서 최초의 thread는 subsribe()가 호출되는 scope에 있는 thread이다.

![photo_2024-06-17_16-22-51](https://github.com/bckkingkkang/WebFlux/assets/131218470/ef8828f1-ba0f-43a3-b7b0-80e91137874f)

### publishOn()과 subscribeOn()의 동작 이해 2
- Operator 체인에서 publishOn()이 호출되면 publishOn() 호출 이후의 Operator 체인은 **다음 publishOn()을 만나기 전까지** publishOn()에서 지정한 Thread에서 실행이 된다.

![photo_2024-06-17_16-48-46](https://github.com/bckkingkkang/WebFlux/assets/131218470/4d9ca0ce-b62c-48b8-89c6-9f2ea40013c8)
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/520564b6-77df-4aa0-844b-bde6658bdeff)

### publishOn()과 subscribeOn()의 동작 이해 3
- Operator 체인에서 publishOn()이 호출되면 publishOn() 호출 이후의 Operator 체인은 **다음 publishOn()을 만나기 전까지** publishOn()에서 지정한 Thread에서 실행이 된다.

<img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/6ec4ead7-a98e-4974-9ba7-add0345cb5c1" width="500" height="500"/>   

```java
@Slf4j
public class SchedulerOperatorExample03 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("doOnNext : {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter : {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map : {}", data))
                .subscribe(data -> log.info("onNext : {}", data));
        Thread.sleep(500L);
    }
}
```
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/31d765db-8fe7-4ae3-9052-29bdfc715c86)

### publishOn()과 subscribeOn()의 동작 이해 4
- subscribeOn()은 **최상위 Upstream publisher의 실행 쓰레드**를 subscribe() 호출 scope의 쓰레드에서 subscribeOn()에서 **지정한 쓰레드로 바꾼다**.
> `메인 쓰레드를 다른 쓰레드로 바꿔주는 역할을 한다.`
<img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/81f5a026-7042-456b-b31d-2a7ff04cb145" width="500"/>   

### publishOn()과 subscribeOn()의 동작 이해 5
- subscribeOn()과 publishOn()이 같이 있다면, publishOn()을 만나기 전까지의 Upstream Operator 체인은 subscribeOn()에서 지정한 쓰레드에서 실행되고, publishOn()을 만날 때마다 publishOn() 아래의 Operator 체인 downstream은 publishOn()에서 지정한 쓰레드에서 실행된다.   
<img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/9302862b-6d63-4fbb-bd1a-9542533f694b" width="500"/>

```java
@Slf4j
public class SchedulerOperatorExample05 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7,})
                .subscribeOn(Schedulers.boundedElastic())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter : {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map : {}", data))
                .subscribe(data -> log.info("# doOnNext subscribe : {}", data));

        Thread.sleep(500L);
    }
}
```   
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/305e1ab7-57f6-4cae-bcf0-e598640a9cba)

### publishOn()과 subscribeOn()의 동작 이해 6
- subscribeOn()이 publishOn() 뒤에 위치하든 상관없이 publishOn()을 만나기 전까지의 Upstream Operator 체인은 subscribeOn()에서 지정한 쓰레드에서 실행된다.

<img src="https://github.com/bckkingkkang/WebFlux/assets/131218470/14ac2bcf-d2d2-48ba-8b16-b6935919b486" width="500"/>   

```java
@Slf4j
public class SchedulerOperatorExample06 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7,})
                .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .subscribeOn(Schedulers.boundedElastic())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# doOnNext subscribe: {}", data));

        Thread.sleep(500L);
    }
}
```

![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/b94296d7-156f-43e6-b21f-d37d4ec4048c)   

### Scheduler의 종류
`Schedulers 클래스의 정적 메소드로 제공됨`   
* **Schedulers.immediate()**
  * 별도의 쓰레드를 추가 할당하지 않고, 현재 쓰레드에서 실행된다.
* **Schedulers.single()**
  * 하나의 쓰레드를 재사용한다.   
  > 쓰레드를 하나만 생성해서 스케줄러가 제거되기 전까지 재사용, 저지연(low latency) 일회성 실행에 최적화 되어 있다.    
* **Schedulers.boundedElastic()**   
  * 쓰레드 풀을 생성하여 생성된 쓰레드를 재사용한다.   
  > 생성된 Thread pool(executor Service 기반의 Thread Pool) 안에서 정해진 수만큼의 Thread를 사용해서 작업을 처리하고, 작업이 종료되면 해당 Thread를 반납해서 재사용하는 방식    
  * 생성할 수 있는 쓰레드 수에 제한이 있다. (Default, CPU 코어 갯수 * 10)   
  * 긴 실행 시간을 가질 수 있는 Blocking I/O 작업에 최적화 되어 있다.   
  > subscribeOn()이라는 Scheduler 전용 Operator에서 주로 사용   
  > 실제 데이터베이스나 Http request 같은 blocking I/O 작업을 통해서 대량의 데이터를 데이터 소스로 사용하는 경우가 많음. 이런 경우 대량의 데이터를 입력으로 받아들이고 출력으로 내보는 경우 실행시간이 길어진다.   
  > 따라서 다른 Non-Blocking I/O 작업에 영향을 주지 않기 위해 BoundedElastic 같은 Scheduler를 이용해 Blocking I/O를 처리하기 위한 전용 Thread를 할당해서 작업 처리 시간을 효율적으로 사용할 수 있다.
* **Schedulers.paralle()**
  * 여러 개의 쓰레드를 할당해서 동시에 작업을 수행할 수 있다.   
  * Non-Blocking I/O 작업에 최적화 되어 있다.   
  > CPU 코어 갯수 만큼의 Thread 생성
* **Schedulers.formExecutorService()**
  * 기존의 ExecutorService를 사용하여 쓰레드를 생성한다.   
  * 의미있는 식별자를 제공하기 때문에 [Metric](#metric)에서 주로 사용된다.   
* **Schedulers.newXXX**
  * 다양한 유형의 새로운 Scheduler를 생성할 수 있다.
    `new Single()`, `newParallel()`, `newboundedElastic()`   
  * Scheduler의 이름을 직접 지정할 수 있다.   
















-------------------------------------------------------------------------------------------------
참고   
[Spring MVC vs Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux/new-framework.html)     
[Reactive Microservices With Spring Boot](https://spring.io/reactive)   
https://poiemaweb.com/

#### Buffer
`완화하다`, `완충제`, `임시 저장 공간`   
> 하나의 장치에서 다른 장치로 데이터를 전송할 경우에 양자 간의 데이터의 전송 속도나 처리 속도의 차를 보상하여 양호하게 결합할 목적으로 사용하는 기억 영역

#### CPU 코어 갯수
![image](https://github.com/bckkingkkang/WebFlux/assets/131218470/1548e46f-d883-4549-bb79-fca829c2b095)

#### Thread
* 운영체제에서 Thread는 프로세스 내부에서 실행되는 작은 작업 단위이다.
* Thread는 process의 실행 흐름을 구성하는 단위로, 하나의 프로세스는 내부에 여러 개의 thread가 포함될 수 있다.
> * 특징   
> 1. 프로세스들이 서로의 데이터에 접근하는 것이 직접적으로 불가능한 반면, 스레드는 프로세스 내부에 존재하기 때문에 프로세스의 데이터 영역에 접근이 가능하다. 또한 Thread끼리의 서로의 데이터에 접근 가능하다.   
> 2. Thread 또한 일종의 작업 단위이기 때문에 프로세스처럼 작업이 처리된다. 즉, Thread도 프로세스처럼 작업을 병렬로 처리해서 속도를 높일 수 있다.   
> 3. Thread는 일종의 함수로 구현된다. 데이터(지역 변수)를 다루고, 데이터를 관리하기 위한 stack 메모리 영역을 가진다. 이 stack 공간은 프로세스의 stack 메모리 영역과는 별개이며, Thread Stack이라고 부른다.   
>> 프로세스는 메모리 영역을 크게 code, data, stack, heap으로 나눌 수 있지만, Thread는 thread stack 메모리 공간만 가진다. (code, data, heap 영역 공유)
>> Thread 간 컨텍스트 스위칭 시 보다 적은 정보를 저장하고 복원하기 때문에 프로세스 간 컨텍스트 스위칭 보다 적은 시간이 소요된다.    
> 4. 하나의 process는 내부에 여러 개의 Thread를 가질 수 있다.   

|장점|단점|
|:-------------------------:|:-----------------------------:|
| **응답성(성능) 향상**, 스레드 간의 작업 분할과 병렬 처리로 인해 사용자가 응용 프로그램을 원할하게 사용하는 데 빠른 응답성을 제공 | **Thread 간의 상호 간섭**, 멀티 쓰레드 실행의 경우, 쓰레드가 다른 쓰레드의 작업을 방해하거나 우선 순위 설정에 문제가 있을 경우 서로에게 영향을 미친다 |
| **자원 공유 효율성 향상**, 하나의 프로세스 내에서 실행, 프로세스 내부에 있기 때문에 별도의 메모리 공간을 할당할 필요가 없음. (쓰레드는 프로세스 내부에서 thread stack 공간에 데이터를 관리한다.) | **성능 저하**, 많은 쓰레드 생성 시 성능 저하 발생 가능, 각각의 스레드들이 병렬로 실행되기 위해 컨텍스트 스위칭이 빈번하게 발생한다. |
| **간결성**, 작업을 분리할 수 있기 때문에 코드가 간결해질 수 있음 | **자원 소비**, 쓰레드는 개별적인 실행 흐름을 가지기 때문에, Thread 마다 stack 및 레지스터 등의 메모리 자원을 소비한다. 따라서 쓰레드의 수가 증가하면 메모리 사용량도 증가하게 되어 시스템 자원이 한계에 도달할 수 있다. |
|  | **동기화 이슈**, 여러 스레드가 공유 자원에 동시에 접근할 때 동기화 문제 발생 가능, 경쟁 상태(Race Condition) 문제는 Thread 간의 실행 순서나 타이밍에 따라 예측할 수 없는 결과가 발생할 수 있다. |

멀티 스레드는 하나의 프로세스 내부에서 여러 개의 스레드가 동시에 실행되는 것이다. 스레드끼리는 서로의 메모리 공간(thread stack)을 공유하고 접근할 수 있다. IPC 통신이 아닌 메모리 기반 통신을 사용하기에 통신 속도가 빠르다. 각 스레드들은 여러 자원을 공유하기에, 하나의 스레드에 문제가 생기면 나머지 스레드들도 영향을 받을 수 있다. 프로세스 내에서 스레드의 작업을 여러개로 분할(쪼개서) 병렬로 처리할 수 있다.   

멀티 프로세스는 여러 개의 독립적인 프로세스가 동시에 실행되는 것이다. 각 프로세스는 독립된 메모리 공간을 가지며, 서로에게 접근하려면 IPC 기법을 사용해야 한다. 각 프로세스는 각각 고유한 자원을 관리하고 있어 서로에게 영향을 미치지 않는다. 하나의 프로세스 작업을 여러개로 분할(쪼개서) 병렬로 처리할 수 있다. 이때, 프로세스는 스레드 단위로 작업을 분할한다.   

#### Metric
* 시간이 지남에 따라 변화하는 데이터를 의미한다. 메모리 사용률, CPU 사용률, 스레드 사용률 등등.. 시간에 따른 추이를 추적할 가치가 있는 데이터   
[참고](https://lordofkangs.tistory.com/326)
