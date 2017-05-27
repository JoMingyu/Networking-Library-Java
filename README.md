# Simple-Networking-Library
HttpURLConnection을 이용해 REST로 구성된 서버와 통신하기 위한 Java 라이브러리

## 사용
### 프로젝트 구성
JSON 라이브러리가 적용된 Maven 프로젝트
### HttpClient 객체 생성해보기
	HttpClient client = new HttpClient();
### 객체 생성의 여러가지 방법
#### 추가 필요
### Config 객체 사용하기
	Config config = new Config();
	config.setTargetAddress("http://127.0.0.1");
	HttpClient client = new HttpClient();
### Config 객체 생성의 여러가지 방법
#### 추가 필요
### HTTP 요청 보내기 : GET
#### 헤더와 파라미터가 없는 GET 요청
	HttpClient client = new HttpClient(config);
	client.get("/test-uri", new HashMap<String, Object>(), new HashMap<String, Object>());
#### GET 요청에 헤더 붙이기
	HttpClient client = new HttpClient(config);
	Map<String, Object> headers = new HashMap<String, Object>();
	headers.put("header", "value");
	
	client.get("/test-uri", headers, new HashMap<String, Object>());
#### 헤더와 파라미터가 있는 GET 요청
	HttpClient client = new HttpClient(config);
	Map<String, Object> headers = new HashMap<String, Object>();
	headers.put("header", "value");
	
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("key", "value");
	
	client.get("/test-uri", headers, params);
#### GET 요청의 응답 얻어오기
	Response response = client.get("/test-uri", headers, params);
	Map<String, List<String>> header = response.getResponseHeader();
	String responseBody = response.getResponseBody();
	int responseCode = response.getResponseCode();
### HTTP 요청 보내기 : POST
#### 요청 본문이 없는 POST 요청
	HttpClient client = new HttpClient(config);
	client.post("/test-uri", new HashMap<String, Object>(), new HashMap<String, Object>());
#### 헤더가 있는 POST 요청
	HttpClient client = new HttpClient(config);
	Map<String, Object> headers = new HashMap<String, Object>();
	headers.put("header", "value");
	
	client.post("/test-uri", headers, new HashMap<String, Object>());
#### 헤더와 요청 본문이 있는 POST 요청
	HttpClient client = new HttpClient(config);
	Map<String, Object> headers = new HashMap<String, Object>();
	headers.put("header", "value");
	
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("key", "value");
	
	client.post("/test-uri", headers, params);
#### POST 요청의 응답 얻어오기
	Response response = client.post("/test-uri", headers, params);
	Map<String, List<String>> header = response.getResponseHeader();
	String responseBody = response.getResponseBody();
	int responseCode = response.getResponseCode();
### 단일 URI의 슬래시(/) 생략하기
	client.get("test-uri", headers, params);
	client.post("test-uri", headers, params);
