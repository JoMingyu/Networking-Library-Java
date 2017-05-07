# Simple-Networking-Library
HttpURLConnection을 이용해 REST로 구성된 서버와 통신하기 위한 Java 라이브러리

## 사용
### 초기 구성
	HttpClientConfig config = new HttpClientConfig();
	config.setTargetAddress("http://127.0.0.1");
	config.setTargetPort(5000);
	config.setReadTimeout(1500);
	config.setConnectTimeout(1500);
#### 꼭 필요한 설정
	HttpClientConfig.setTargetAddress("http://127.0.0.1");
#### 기본 설정들
    targetPort = 80
    readTimeout = 3000
    connectTimeout = 3000
### HttpClient 객체 생성해보기
	try {
		HttpClient client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
#### try-catch 블럭 아끼기
	HttpClient client = null;
	try {
		client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
### HTTP 요청 보내기 : GET
#### 파라미터가 없는 GET 요청
	HttpClient client = null;
	try {
		client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
	client.get("/test-uri");
#### 파라미터가 있는 GET 요청
	HttpClient client = null;
	try {
		client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("sample_key", "sample_value");
	params.put("sample_key_2", 20120);
	client.get("/test-uri", params);
#### GET 요청의 응답 얻어오기
	Map<String, Object> response = client.get("/test-uri", params);
	String response = responseMap.get("response").toString();
	int responseCode = Integer.valueOf(responseMap.get("code").toString());
### HTTP 요청 보내기 : POST
#### 요청 본문이 없는 POST 요청
	HttpClient client = null;
	try {
		client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
	client.post("/test-uri");
#### 요청 본문이 있는 POST 요청
	HttpClient client = null;
	try {
		client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("sample_key", "sample_value");
	params.put("sample_key_2", 20120);
	client.post("/test-uri", params);
#### POST 요청의 응답 코드 얻어오기
	int responseCode = client.post("/test-uri", params);
### 단일 URI의 슬래시(/) 생략하기
	client.get("test-uri");
	client.post("test-uri");
