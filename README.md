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

#### 다른 설정들은?
    targetPort = 80
    readTimeout = 3000;
    connectTimeout = 3000;

### HttpClient 객체 생성해보기
	try {
		HttpClient client = new HttpClient();
	} catch (TargetAddressNotDeclaredException e) {
		e.printStackTrace();
	}
