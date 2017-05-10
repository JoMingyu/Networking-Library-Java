# Simple-Networking-Library
HttpURLConnection을 이용해 REST로 구성된 서버와 통신하기 위한 Java 라이브러리

## 사용
### 조건
JSON 라이브러리 필요
### 설정
	Config config = new HttpClientConfig();
	config.setTargetAddress("http://127.0.0.1");
	config.setTargetPort(5000);
	config.setReadTimeout(1500);
	config.setConnectTimeout(1500);
#### 꼭 필요한 설정
	config.setTargetAddress("http://127.0.0.1");
#### 다른 데이터들을 따로 설정하지 않았을 경우
	targetPort = 80
	readTimeout = 3000
	connectTimeout = 3000
### HttpClient 객체 생성해보기
	Config config = new HttpClientConfig();
	config.setTargetAddress("http://127.0.0.1");
	HttpClient client = new HttpClient(config);
### 언제나 똑같은 설정을 사용하고 싶은 경우
##### HttpClientDefaultConfig 클래스 사용, HttpClient의 생성자에 아무것도 보내지 않음
	Config config = new HttpClientDefaultConfig();
	config.setTargetAddress("http://127.0.0.1");
	HttpClient client = new HttpClient();
##### 원리 : 생성자 오버로딩
	public class HttpClient {
		private Config config = null;
	
		public HttpClient(HttpClientConfig config) {
			this.config = config;
		}
	
		public HttpClient() {
			this.config = new HttpClientDefaultConfig();
		}
	}
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
	Map<String, Object> responseMap = client.get("/test-uri", headers, params);
	String response = responseMap.get("response").toString();
	int responseCode = Integer.valueOf(responseMap.get("code").toString());
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
	String response = responseMap.get("response").toString();
	int responseCode = Integer.valueOf(responseMap.get("code").toString());
#### POST 요청의 응답 얻어오기
	int responseCode = client.post("/test-uri", headers, params);
### 단일 URI의 슬래시(/) 생략하기
	client.get("test-uri", headers, params);
	client.post("test-uri", headers, params);
