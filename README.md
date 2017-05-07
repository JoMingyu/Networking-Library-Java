# Simple-Networking-Library
HttpURLConnection을 이용해 REST로 구성된 서버와 통신하기 위한 Java 라이브러리

## 사용

### 초기 구성
    HttpClientConfig.setTargetAddress("http://127.0.0.1");
    HttpClientConfig.setTargetPort(5000);
    HttpClientConfig.setReadTimeout(1500);
    HttpClientConfig.setConnectTimeout(1500);
