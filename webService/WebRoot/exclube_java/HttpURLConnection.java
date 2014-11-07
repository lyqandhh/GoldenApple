package sun.net.www.protocol.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.Authenticator.RequestorType;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.ResponseCache;
import java.net.SecureCacheResponse;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import sun.misc.JavaNetHttpCookieAccess;
import sun.misc.SharedSecrets;
import sun.net.ApplicationProxy;
import sun.net.ProgressMonitor;
import sun.net.ProgressSource;
import sun.net.www.HeaderParser;
import sun.net.www.MessageHeader;
import sun.net.www.MeteredStream;
import sun.net.www.ParseUtil;
import sun.net.www.http.ChunkedInputStream;
import sun.net.www.http.ChunkedOutputStream;
import sun.net.www.http.HttpClient;
import sun.net.www.http.PosterOutputStream;
import sun.security.action.GetBooleanAction;
import sun.security.action.GetIntegerAction;
import sun.security.action.GetPropertyAction;
import sun.util.logging.PlatformLogger;

public class HttpURLConnection extends HttpURLConnection
{
  static String HTTP_CONNECT;
  static final String version;
  public static final String userAgent;
  static final int defaultmaxRedirects = 20;
  static final int maxRedirects;
  static final boolean validateProxy;
  static final boolean validateServer;
  private StreamingOutputStream strOutputStream;
  private static final String RETRY_MSG1 = "cannot retry due to proxy authentication, in streaming mode";
  private static final String RETRY_MSG2 = "cannot retry due to server authentication, in streaming mode";
  private static final String RETRY_MSG3 = "cannot retry due to redirection, in streaming mode";
  private static boolean enableESBuffer;
  private static int timeout4ESBuffer;
  private static int bufSize4ES;
  private static final boolean allowRestrictedHeaders;
  private static final Set<String> restrictedHeaderSet;
  private static final String[] restrictedHeaders;
  static final String httpVersion = "HTTP/1.1";
  static final String acceptString = "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
  private static final String[] EXCLUDE_HEADERS;
  private static final String[] EXCLUDE_HEADERS2;
  protected HttpClient http;
  protected Handler handler;
  protected Proxy instProxy;
  private CookieHandler cookieHandler;
  private ResponseCache cacheHandler;
  protected CacheResponse cachedResponse;
  private MessageHeader cachedHeaders;
  private InputStream cachedInputStream;
  protected PrintStream ps;
  private InputStream errorStream;
  private boolean setUserCookies;
  private String userCookies;
  private String userCookies2;
  private static HttpAuthenticator defaultAuth;
  private MessageHeader requests;
  String domain;
  DigestAuthentication.Parameters digestparams;
  AuthenticationInfo currentProxyCredentials;
  AuthenticationInfo currentServerCredentials;
  boolean needToCheck;
  private boolean doingNTLM2ndStage;
  private boolean doingNTLMp2ndStage;
  private boolean tryTransparentNTLMServer;
  private boolean tryTransparentNTLMProxy;
  private Object authObj;
  boolean isUserServerAuth;
  boolean isUserProxyAuth;
  String serverAuthKey;
  String proxyAuthKey;
  protected ProgressSource pi;
  private MessageHeader responses;
  private InputStream inputStream;
  private PosterOutputStream poster;
  private boolean setRequests;
  private boolean failedOnce;
  private Exception rememberedException;
  private HttpClient reuseClient;
  private TunnelState tunnelState;
  private int connectTimeout;
  private int readTimeout;
  private static final PlatformLogger logger;
  String requestURI;
  byte[] cdata;
  private static final String SET_COOKIE = "set-cookie";
  private static final String SET_COOKIE2 = "set-cookie2";
  private java.util.Map<String, List<String>> filteredHeaders;

  private static PasswordAuthentication privilegedRequestPasswordAuthentication(String paramString1, InetAddress paramInetAddress, int paramInt, String paramString2, String paramString3, String paramString4, URL paramURL, Authenticator.RequestorType paramRequestorType)
  {
    return ((PasswordAuthentication)AccessController.doPrivileged(new 1(paramString1, paramURL, paramInetAddress, paramInt, paramString2, paramString3, paramString4, paramRequestorType)));
  }

  private boolean isRestrictedHeader(String paramString1, String paramString2)
  {
    if (allowRestrictedHeaders) {
      return false;
    }

    paramString1 = paramString1.toLowerCase();
    if (restrictedHeaderSet.contains(paramString1))
    {
      return ((!(paramString1.equals("connection"))) || (!(paramString2.equalsIgnoreCase("close"))));
    }

    return (paramString1.startsWith("sec-"));
  }

  private boolean isExternalMessageHeaderAllowed(String paramString1, String paramString2)
  {
    checkMessageHeader(paramString1, paramString2);

    return (!(isRestrictedHeader(paramString1, paramString2)));
  }

  public static PlatformLogger getHttpLogger()
  {
    return logger;
  }

  public Object authObj()
  {
    return this.authObj;
  }

  public void authObj(Object paramObject) {
    this.authObj = paramObject;
  }

  private void checkMessageHeader(String paramString1, String paramString2)
  {
    int i = 10;
    int j = paramString1.indexOf(i);
    if (j != -1) {
      throw new IllegalArgumentException("Illegal character(s) in message header field: " + paramString1);
    }

    if (paramString2 == null) {
      return;
    }

    j = paramString2.indexOf(i);
    while (true) { if (j == -1) return;
      ++j;
      if (j >= paramString2.length()) break;
      int k = paramString2.charAt(j);
      if ((k != 32) && (k != 9))
        break;
      j = paramString2.indexOf(i, j);
    }

    throw new IllegalArgumentException("Illegal character(s) in message header value: " + paramString2);
  }

  private void writeRequests()
    throws IOException
  {
    if ((this.http.usingProxy) && (tunnelState() != TunnelState.TUNNELING))
      setPreemptiveProxyAuthentication(this.requests);

    if (!(this.setRequests))
    {
      if (!(this.failedOnce))
        this.requests.prepend(this.method + " " + getRequestURI() + " " + "HTTP/1.1", null);

      if (!(getUseCaches())) {
        this.requests.setIfNotSet("Cache-Control", "no-cache");
        this.requests.setIfNotSet("Pragma", "no-cache");
      }
      this.requests.setIfNotSet("User-Agent", userAgent);
      int i = this.url.getPort();
      String str2 = this.url.getHost();
      if ((i != -1) && (i != this.url.getDefaultPort()))
        str2 = str2 + ":" + String.valueOf(i);

      this.requests.setIfNotSet("Host", str2);
      this.requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");

      if ((!(this.failedOnce)) && (this.http.getHttpKeepAliveSet())) {
        if ((this.http.usingProxy) && (tunnelState() != TunnelState.TUNNELING))
          this.requests.setIfNotSet("Proxy-Connection", "keep-alive");
        else {
          this.requests.setIfNotSet("Connection", "keep-alive");
        }

      }
      else
      {
        this.requests.setIfNotSet("Connection", "close");
      }

      long l = getIfModifiedSince();
      if (l != 0L) {
        localObject1 = new Date(l);

        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.requests.setIfNotSet("If-Modified-Since", localSimpleDateFormat.format((Date)localObject1));
      }

      Object localObject1 = AuthenticationInfo.getServerAuth(this.url);
      if ((localObject1 != null) && (((AuthenticationInfo)localObject1).supportsPreemptiveAuthorization()))
      {
        this.requests.setIfNotSet(((AuthenticationInfo)localObject1).getHeaderName(), ((AuthenticationInfo)localObject1).getHeaderValue(this.url, this.method));
        this.currentServerCredentials = ((AuthenticationInfo)localObject1);
      }

      if ((!(this.method.equals("PUT"))) && (((this.poster != null) || (streaming())))) {
        this.requests.setIfNotSet("Content-type", "application/x-www-form-urlencoded");
      }

      int k = 0;

      if (streaming()) {
        if (this.chunkLength != -1) {
          this.requests.set("Transfer-Encoding", "chunked");
          k = 1;
        }
        else if (this.fixedContentLengthLong != -1L) {
          this.requests.set("Content-Length", String.valueOf(this.fixedContentLengthLong));
        }
        else if (this.fixedContentLength != -1) {
          this.requests.set("Content-Length", String.valueOf(this.fixedContentLength));
        }

      }
      else if (this.poster != null)
      {
        synchronized (this.poster)
        {
          this.poster.close();
          this.requests.set("Content-Length", String.valueOf(this.poster.size()));
        }

      }

      if ((k == 0) && 
        (this.requests.findValue("Transfer-Encoding") != null)) {
        this.requests.remove("Transfer-Encoding");
        if (logger.isLoggable(900)) {
          logger.warning("use streaming mode for chunked encoding");
        }

      }

      setCookieHeader();

      this.setRequests = true;
    }
    if (logger.isLoggable(500))
      logger.fine(this.requests.toString());

    this.http.writeRequests(this.requests, this.poster, streaming());
    if (this.ps.checkError()) {
      String str1 = this.http.getProxyHostUsed();
      int j = this.http.getProxyPortUsed();
      disconnectInternal();
      if (this.failedOnce)
        throw new IOException("Error writing to server");

      this.failedOnce = true;
      if (str1 != null)
        setProxiedClient(this.url, str1, j);
      else
        setNewClient(this.url);

      this.ps = ((PrintStream)this.http.getOutputStream());
      this.connected = true;
      this.responses = new MessageHeader();
      this.setRequests = false;
      writeRequests();
    }
  }

  protected void setNewClient(URL paramURL)
    throws IOException
  {
    setNewClient(paramURL, false);
  }

  protected void setNewClient(URL paramURL, boolean paramBoolean)
    throws IOException
  {
    this.http = HttpClient.New(paramURL, null, -1, paramBoolean, this.connectTimeout, this);
    this.http.setReadTimeout(this.readTimeout);
  }

  protected void setProxiedClient(URL paramURL, String paramString, int paramInt)
    throws IOException
  {
    setProxiedClient(paramURL, paramString, paramInt, false);
  }

  protected void setProxiedClient(URL paramURL, String paramString, int paramInt, boolean paramBoolean)
    throws IOException
  {
    proxiedConnect(paramURL, paramString, paramInt, paramBoolean);
  }

  protected void proxiedConnect(URL paramURL, String paramString, int paramInt, boolean paramBoolean)
    throws IOException
  {
    this.http = HttpClient.New(paramURL, paramString, paramInt, paramBoolean, this.connectTimeout, this);

    this.http.setReadTimeout(this.readTimeout);
  }

  protected HttpURLConnection(URL paramURL, Handler paramHandler)
    throws IOException
  {
    this(paramURL, null, paramHandler);
  }

  public HttpURLConnection(URL paramURL, String paramString, int paramInt) {
    this(paramURL, new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(paramString, paramInt)));
  }

  public HttpURLConnection(URL paramURL, Proxy paramProxy)
  {
    this(paramURL, paramProxy, new Handler());
  }

  protected HttpURLConnection(URL paramURL, Proxy paramProxy, Handler paramHandler) {
    super(paramURL);

    this.ps = null;

    this.errorStream = null;

    this.setUserCookies = true;
    this.userCookies = null;
    this.userCookies2 = null;

    this.currentProxyCredentials = null;
    this.currentServerCredentials = null;
    this.needToCheck = true;
    this.doingNTLM2ndStage = false;
    this.doingNTLMp2ndStage = false;

    this.tryTransparentNTLMServer = true;
    this.tryTransparentNTLMProxy = true;

    this.inputStream = null;

    this.poster = null;

    this.setRequests = false;

    this.failedOnce = false;

    this.rememberedException = null;

    this.reuseClient = null;

    this.tunnelState = TunnelState.NONE;

    this.connectTimeout = -1;
    this.readTimeout = -1;

    this.requestURI = null;

    this.cdata = new byte[128];

    this.requests = new MessageHeader();
    this.responses = new MessageHeader();
    this.handler = paramHandler;
    this.instProxy = paramProxy;
    if (this.instProxy instanceof ApplicationProxy)
    {
      try
      {
        this.cookieHandler = CookieHandler.getDefault(); } catch (SecurityException localSecurityException) {
      }
    }
    else { this.cookieHandler = ((CookieHandler)AccessController.doPrivileged(new 2(this)));
    }

    this.cacheHandler = ((ResponseCache)AccessController.doPrivileged(new 3(this)));
  }

  public static void setDefaultAuthenticator(HttpAuthenticator paramHttpAuthenticator)
  {
    defaultAuth = paramHttpAuthenticator;
  }

  public static InputStream openConnectionCheckRedirects(URLConnection paramURLConnection)
    throws IOException
  {
    int i;
    InputStream localInputStream;
    int j = 0;
    do
    {
      if (paramURLConnection instanceof HttpURLConnection) {
        ((HttpURLConnection)paramURLConnection).setInstanceFollowRedirects(false);
      }

      localInputStream = paramURLConnection.getInputStream();
      i = 0;

      if (paramURLConnection instanceof HttpURLConnection) {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURLConnection;
        int k = localHttpURLConnection.getResponseCode();
        if ((k >= 300) && (k <= 307) && (k != 306) && (k != 304))
        {
          URL localURL1 = localHttpURLConnection.getURL();
          String str = localHttpURLConnection.getHeaderField("Location");
          URL localURL2 = null;
          if (str != null)
            localURL2 = new URL(localURL1, str);

          localHttpURLConnection.disconnect();
          if ((localURL2 == null) || (!(localURL1.getProtocol().equals(localURL2.getProtocol()))) || (localURL1.getPort() != localURL2.getPort()) || (!(hostsEqual(localURL1, localURL2))) || (j >= 5))
          {
            throw new SecurityException("illegal URL redirect");
          }
          i = 1;
          paramURLConnection = localURL2.openConnection();
          ++j; }
      }
    }
    while (i != 0);
    return localInputStream;
  }

  private static boolean hostsEqual(URL paramURL1, URL paramURL2)
  {
    String str1 = paramURL1.getHost();
    String str2 = paramURL2.getHost();

    if (str1 == null)
      return (str2 == null);
    if (str2 == null)
      return false;
    if (str1.equalsIgnoreCase(str2)) {
      return true;
    }

    boolean[] arrayOfBoolean = { false };

    AccessController.doPrivileged(new 4(str1, str2, arrayOfBoolean));

    return arrayOfBoolean[0];
  }

  public void connect()
    throws IOException
  {
    plainConnect();
  }

  private boolean checkReuseConnection() {
    if (this.connected)
      return true;

    if (this.reuseClient != null) {
      this.http = this.reuseClient;
      this.http.setReadTimeout(getReadTimeout());
      this.http.reuse = false;
      this.reuseClient = null;
      this.connected = true;
      return true;
    }
    return false;
  }

  protected void plainConnect() throws IOException {
    if (this.connected) {
      return;
    }

    if ((this.cacheHandler != null) && (getUseCaches())) {
      try {
        URI localURI1 = ParseUtil.toURI(this.url);
        if (localURI1 != null) {
          this.cachedResponse = this.cacheHandler.get(localURI1, getRequestMethod(), this.requests.getHeaders(EXCLUDE_HEADERS));
          if (("https".equalsIgnoreCase(localURI1.getScheme())) && (!(this.cachedResponse instanceof SecureCacheResponse)))
          {
            this.cachedResponse = null;
          }
          if (logger.isLoggable(300)) {
            logger.finest("Cache Request for " + localURI1 + " / " + getRequestMethod());
            logger.finest("From cache: " + ((this.cachedResponse != null) ? this.cachedResponse.toString() : "null"));
          }
          if (this.cachedResponse != null) {
            this.cachedHeaders = mapToMessageHeader(this.cachedResponse.getHeaders());
            this.cachedInputStream = this.cachedResponse.getBody();
          }
        }
      }
      catch (IOException localIOException1) {
      }
      if ((this.cachedHeaders != null) && (this.cachedInputStream != null)) {
        this.connected = true;
        return;
      }
      this.cachedResponse = null;
    }

    try
    {
      if (this.instProxy == null)
      {
        ProxySelector localProxySelector = (ProxySelector)AccessController.doPrivileged(new 5(this));

        if (localProxySelector != null) {
          URI localURI2 = ParseUtil.toURI(this.url);
          if (logger.isLoggable(300))
            logger.finest("ProxySelector Request for " + localURI2);

          Iterator localIterator = localProxySelector.select(localURI2).iterator();

          while (localIterator.hasNext()) {
            Proxy localProxy = (Proxy)localIterator.next();
            try {
              if (!(this.failedOnce)) {
                this.http = getNewHttpClient(this.url, localProxy, this.connectTimeout);
                this.http.setReadTimeout(this.readTimeout);
              }
              else
              {
                this.http = getNewHttpClient(this.url, localProxy, this.connectTimeout, false);
                this.http.setReadTimeout(this.readTimeout);
              }
              label527: label530: if ((logger.isLoggable(300)) && 
                (localProxy != null))
                logger.finest("Proxy used: " + localProxy.toString());

            }
            catch (IOException localIOException3)
            {
              if (localProxy != Proxy.NO_PROXY) {
                localProxySelector.connectFailed(localURI2, localProxy.address(), localIOException3);
                if (localIterator.hasNext())
                  break label527;
                this.http = getNewHttpClient(this.url, null, this.connectTimeout, false);
                this.http.setReadTimeout(this.readTimeout);
                break label530:
              }

              throw localIOException3;
            }

          }

        }
        else if (!(this.failedOnce)) {
          this.http = getNewHttpClient(this.url, null, this.connectTimeout);
          this.http.setReadTimeout(this.readTimeout);
        }
        else
        {
          this.http = getNewHttpClient(this.url, null, this.connectTimeout, false);
          this.http.setReadTimeout(this.readTimeout);
        }

      }
      else if (!(this.failedOnce)) {
        this.http = getNewHttpClient(this.url, this.instProxy, this.connectTimeout);
        this.http.setReadTimeout(this.readTimeout);
      }
      else
      {
        this.http = getNewHttpClient(this.url, this.instProxy, this.connectTimeout, false);
        this.http.setReadTimeout(this.readTimeout);
      }

      this.ps = ((PrintStream)this.http.getOutputStream());
    } catch (IOException localIOException2) {
      throw localIOException2;
    }

    this.connected = true;
  }

  protected HttpClient getNewHttpClient(URL paramURL, Proxy paramProxy, int paramInt)
    throws IOException
  {
    return HttpClient.New(paramURL, paramProxy, paramInt, this);
  }

  protected HttpClient getNewHttpClient(URL paramURL, Proxy paramProxy, int paramInt, boolean paramBoolean)
    throws IOException
  {
    return HttpClient.New(paramURL, paramProxy, paramInt, paramBoolean, this);
  }

  private void expect100Continue()
    throws IOException
  {
    int i = this.http.getReadTimeout();
    int j = 0;
    int k = 0;
    if (i <= 0)
    {
      this.http.setReadTimeout(5000);
      j = 1;
    }
    try
    {
      this.http.parseHTTP(this.responses, this.pi, this);
    } catch (SocketTimeoutException localSocketTimeoutException) {
      if (j == 0)
        throw localSocketTimeoutException;

      k = 1;
      this.http.setIgnoreContinue(true);
    }
    if (k == 0)
    {
      String str = this.responses.getValue(0);

      if ((str != null) && (str.startsWith("HTTP/"))) {
        String[] arrayOfString = str.split("\\s+");
        this.responseCode = -1;
        try
        {
          if (arrayOfString.length > 1)
            this.responseCode = Integer.parseInt(arrayOfString[1]);
        } catch (NumberFormatException localNumberFormatException) {
        }
      }
      if (this.responseCode != 100)
        throw new ProtocolException("Server rejected operation");

    }

    this.http.setReadTimeout(i);

    this.responseCode = -1;
    this.responses.reset();
  }

  public synchronized OutputStream getOutputStream()
    throws IOException
  {
    try
    {
      if (!(this.doOutput)) {
        throw new ProtocolException("cannot write to a URLConnection if doOutput=false - call setDoOutput(true)");
      }

      if (this.method.equals("GET"))
        this.method = "POST";

      if ((!("POST".equals(this.method))) && (!("PUT".equals(this.method))) && ("http".equals(this.url.getProtocol())))
      {
        throw new ProtocolException("HTTP method " + this.method + " doesn't support output");
      }

      if (this.inputStream != null) {
        throw new ProtocolException("Cannot write output after reading input.");
      }

      if (!(checkReuseConnection()))
        connect();

      int i = 0;
      String str = this.requests.findValue("Expect");
      if ("100-Continue".equalsIgnoreCase(str)) {
        this.http.setIgnoreContinue(false);
        i = 1;
      }

      if ((streaming()) && (this.strOutputStream == null)) {
        writeRequests();
      }

      if (i != 0)
        expect100Continue();

      this.ps = ((PrintStream)this.http.getOutputStream());
      if (streaming()) {
        if (this.strOutputStream == null)
          if (this.chunkLength != -1) {
            this.strOutputStream = new StreamingOutputStream(this, new ChunkedOutputStream(this.ps, this.chunkLength), -1L);
          }
          else {
            long l = 0L;
            if (this.fixedContentLengthLong != -1L)
              l = this.fixedContentLengthLong;
            else if (this.fixedContentLength != -1)
              l = this.fixedContentLength;

            this.strOutputStream = new StreamingOutputStream(this, this.ps, l);
          }

        return this.strOutputStream;
      }
      if (this.poster == null)
        this.poster = new PosterOutputStream();

      return this.poster;
    }
    catch (RuntimeException localRuntimeException) {
      disconnectInternal();
      throw localRuntimeException;
    }
    catch (ProtocolException localProtocolException)
    {
      int j = this.responseCode;
      disconnectInternal();
      this.responseCode = j;
      throw localProtocolException;
    } catch (IOException localIOException) {
      disconnectInternal();
      throw localIOException;
    }
  }

  public boolean streaming() {
    return ((this.fixedContentLength != -1) || (this.fixedContentLengthLong != -1L) || (this.chunkLength != -1));
  }

  private void setCookieHeader()
    throws IOException
  {
    if (this.cookieHandler != null)
    {
      label411: int j;
      synchronized (this) {
        if (this.setUserCookies) {
          int i = this.requests.getKey("Cookie");
          if (i != -1)
            this.userCookies = this.requests.getValue(i);
          i = this.requests.getKey("Cookie2");
          if (i != -1)
            this.userCookies2 = this.requests.getValue(i);
          this.setUserCookies = false;
        }

      }

      this.requests.remove("Cookie");
      this.requests.remove("Cookie2");

      ??? = ParseUtil.toURI(this.url);
      if (??? != null) {
        if (logger.isLoggable(300))
          logger.finest("CookieHandler request for " + ???);

        java.util.Map localMap = this.cookieHandler.get((URI)???, this.requests.getHeaders(EXCLUDE_HEADERS));

        if (!(localMap.isEmpty())) {
          if (logger.isLoggable(300)) {
            logger.finest("Cookies retrieved: " + localMap.toString());
          }

          Iterator localIterator1 = localMap.entrySet().iterator();
          while (true) { Map.Entry localEntry;
            String str1;
            while (true) { if (!(localIterator1.hasNext())) break label411; localEntry = (Map.Entry)localIterator1.next();
              str1 = (String)localEntry.getKey();

              if (("Cookie".equalsIgnoreCase(str1)) || ("Cookie2".equalsIgnoreCase(str1)))
                break;
            }

            List localList = (List)localEntry.getValue();
            if ((localList != null) && (!(localList.isEmpty()))) {
              StringBuilder localStringBuilder = new StringBuilder();
              for (String str2 : localList)
                localStringBuilder.append(str2).append("; ");

              try
              {
                this.requests.add(str1, localStringBuilder.substring(0, localStringBuilder.length() - 2));
              }
              catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException) {
              }
            }
          }
        }
      }
      if (this.userCookies != null)
      {
        if ((j = this.requests.getKey("Cookie")) != -1)
          this.requests.set("Cookie", this.requests.getValue(j) + ";" + this.userCookies);
        else
          this.requests.set("Cookie", this.userCookies);
      }
      if (this.userCookies2 != null)
      {
        if ((j = this.requests.getKey("Cookie2")) != -1)
          this.requests.set("Cookie2", this.requests.getValue(j) + ";" + this.userCookies2);
        else
          this.requests.set("Cookie2", this.userCookies2);
      }
    }
  }

  public synchronized InputStream getInputStream()
    throws IOException
  {
    if (!(this.doInput)) {
      throw new ProtocolException("Cannot read from URLConnection if doInput=false (call setDoInput(true))");
    }

    if (this.rememberedException != null) {
      if (this.rememberedException instanceof RuntimeException)
        throw new RuntimeException(this.rememberedException);

      throw getChainedException((IOException)this.rememberedException);
    }

    if (this.inputStream != null) {
      return this.inputStream;
    }

    if (streaming()) {
      if (this.strOutputStream == null) {
        getOutputStream();
      }

      this.strOutputStream.close();
      if (!(this.strOutputStream.writtenOK()))
        throw new IOException("Incomplete output stream");

    }

    int i = 0;
    int j = 0;
    long l = -1L;
    Object localObject1 = null;
    AuthenticationInfo localAuthenticationInfo = null;
    AuthenticationHeader localAuthenticationHeader = null;

    int k = 0;
    int i1 = 0;

    this.isUserServerAuth = (this.requests.getKey("Authorization") != -1);
    this.isUserProxyAuth = (this.requests.getKey("Proxy-Authorization") != -1);
    try
    {
      boolean bool4;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      if (!(checkReuseConnection()))
        connect();

      if (this.cachedInputStream != null) {
        InputStream localInputStream = this.cachedInputStream;

        return localInputStream;
      }
      boolean bool1 = ProgressMonitor.getDefault().shouldMeterInput(this.url, this.method);

      if (bool1) {
        this.pi = new ProgressSource(this.url, this.method);
        this.pi.beginTracking();
      }

      this.ps = ((PrintStream)this.http.getOutputStream());

      if (!(streaming()))
        writeRequests();

      this.http.parseHTTP(this.responses, this.pi, this);
      if (logger.isLoggable(500)) {
        logger.fine(this.responses.toString());
      }

      boolean bool2 = this.responses.filterNTLMResponses("WWW-Authenticate");
      boolean bool3 = this.responses.filterNTLMResponses("Proxy-Authenticate");
      if ((((bool2) || (bool3))) && 
        (logger.isLoggable(500))) {
        logger.fine(">>>> Headers are filtered");
        logger.fine(this.responses.toString());
      }

      this.inputStream = this.http.getInputStream();

      j = getResponseCode();
      if (j == -1) {
        disconnectInternal();
        throw new IOException("Invalid Http response");
      }
      if (j == 407) {
        if (streaming()) {
          disconnectInternal();
          throw new HttpRetryException("cannot retry due to proxy authentication, in streaming mode", 407);
        }

        bool4 = false;
        localObject4 = this.responses.multiValueIterator("Proxy-Authenticate");
        while (((Iterator)localObject4).hasNext()) {
          localObject5 = ((String)((Iterator)localObject4).next()).trim();
          if ((((String)localObject5).equalsIgnoreCase("Negotiate")) || (((String)localObject5).equalsIgnoreCase("Kerberos")))
          {
            if (i1 == 0) {
              i1 = 1; break;
            }
            bool4 = true;
            this.doingNTLMp2ndStage = false;
            localAuthenticationInfo = null;

            break;
          }

        }

        localObject5 = new AuthenticationHeader("Proxy-Authenticate", this.responses, new HttpCallerInfo(this.url, this.http.getProxyHostUsed(), this.http.getProxyPortUsed()), bool4);

        if (!(this.doingNTLMp2ndStage)) {
          localAuthenticationInfo = resetProxyAuthentication(localAuthenticationInfo, (AuthenticationHeader)localObject5);

          if (localAuthenticationInfo == null) break label758;
          ++i;
          disconnectInternal();
          break label1846:
        }

        localObject6 = this.responses.findValue("Proxy-Authenticate");
        reset();
        if (!(localAuthenticationInfo.setHeaders(this, ((AuthenticationHeader)localObject5).headerParser(), (String)localObject6)))
        {
          disconnectInternal();
          throw new IOException("Authentication failure");
        }
        if ((localObject1 != null) && (localAuthenticationHeader != null) && (!(((AuthenticationInfo)localObject1).setHeaders(this, localAuthenticationHeader.headerParser(), (String)localObject6))))
        {
          disconnectInternal();
          throw new IOException("Authentication failure");
        }
        this.authObj = null;
        this.doingNTLMp2ndStage = false;
        label758: break label1846:
      }
      else {
        i1 = 0;
        this.doingNTLMp2ndStage = false;
        if (!(this.isUserProxyAuth))
          this.requests.remove("Proxy-Authorization");

      }

      if (localAuthenticationInfo != null)
      {
        localAuthenticationInfo.addToCache();
      }

      if (j == 401) {
        if (streaming()) {
          disconnectInternal();
          throw new HttpRetryException("cannot retry due to server authentication, in streaming mode", 401);
        }

        bool4 = false;
        localObject4 = this.responses.multiValueIterator("WWW-Authenticate");
        while (((Iterator)localObject4).hasNext()) {
          localObject5 = ((String)((Iterator)localObject4).next()).trim();
          if ((((String)localObject5).equalsIgnoreCase("Negotiate")) || (((String)localObject5).equalsIgnoreCase("Kerberos")))
          {
            if (k == 0) {
              k = 1; break;
            }
            bool4 = true;
            this.doingNTLM2ndStage = false;
            localObject1 = null;

            break;
          }
        }

        localAuthenticationHeader = new AuthenticationHeader("WWW-Authenticate", this.responses, new HttpCallerInfo(this.url), bool4);

        localObject5 = localAuthenticationHeader.raw();
        if (!(this.doingNTLM2ndStage)) {
          if ((localObject1 != null) && (((AuthenticationInfo)localObject1).getAuthScheme() != AuthScheme.NTLM))
          {
            if (((AuthenticationInfo)localObject1).isAuthorizationStale((String)localObject5))
            {
              disconnectWeb();
              ++i;
              this.requests.set(((AuthenticationInfo)localObject1).getHeaderName(), ((AuthenticationInfo)localObject1).getHeaderValue(this.url, this.method));

              this.currentServerCredentials = ((AuthenticationInfo)localObject1);
              setCookieHeader();
              break label1846:
            }
            ((AuthenticationInfo)localObject1).removeFromCache();
          }

          localObject1 = getServerAuthentication(localAuthenticationHeader);
          this.currentServerCredentials = ((AuthenticationInfo)localObject1);

          if (localObject1 != null) {
            disconnectWeb();
            ++i;
            setCookieHeader();
          }
        }
        else {
          reset();

          if (!(((AuthenticationInfo)localObject1).setHeaders(this, null, (String)localObject5))) {
            disconnectWeb();
            throw new IOException("Authentication failure");
          }
          this.doingNTLM2ndStage = false;
          this.authObj = null;
          setCookieHeader();
        }
      }
      else
      {
        if (localObject1 != null)
        {
          Object localObject2;
          if ((!(localObject1 instanceof DigestAuthentication)) || (this.domain == null))
          {
            if (localObject1 instanceof BasicAuthentication)
            {
              localObject2 = AuthenticationInfo.reducePath(this.url.getPath());
              localObject4 = ((AuthenticationInfo)localObject1).path;
              if ((!(((String)localObject4).startsWith((String)localObject2))) || (((String)localObject2).length() >= ((String)localObject4).length()))
              {
                localObject2 = BasicAuthentication.getRootPath((String)localObject4, (String)localObject2);
              }

              localObject5 = (BasicAuthentication)((AuthenticationInfo)localObject1).clone();

              ((AuthenticationInfo)localObject1).removeFromCache();
              ((BasicAuthentication)localObject5).path = ((String)localObject2);
              localObject1 = localObject5;
            }
            ((AuthenticationInfo)localObject1).addToCache();
          }
          else {
            localObject2 = (DigestAuthentication)localObject1;

            localObject4 = new StringTokenizer(this.domain, " ");
            localObject5 = ((DigestAuthentication)localObject2).realm;
            localObject6 = ((DigestAuthentication)localObject2).pw;
            this.digestparams = ((DigestAuthentication)localObject2).params;
            while (((StringTokenizer)localObject4).hasMoreTokens()) {
              String str2 = ((StringTokenizer)localObject4).nextToken();
              try
              {
                URL localURL = new URL(this.url, str2);
                DigestAuthentication localDigestAuthentication = new DigestAuthentication(false, localURL, (String)localObject5, "Digest", (PasswordAuthentication)localObject6, this.digestparams);

                localDigestAuthentication.addToCache();
              }
              catch (Exception localException2)
              {
              }
            }
          }
        }

        k = 0;
        i1 = 0;

        this.doingNTLMp2ndStage = false;
        this.doingNTLM2ndStage = false;
        if (!(this.isUserServerAuth))
          this.requests.remove("Authorization");
        if (!(this.isUserProxyAuth))
          this.requests.remove("Proxy-Authorization");

        if (j == 200)
          checkResponseCredentials(false);
        else {
          this.needToCheck = false;
        }

        this.needToCheck = true;

        if (followRedirect())
        {
          ++i;

          setCookieHeader();
        }
        else
        {
          try
          {
            l = Long.parseLong(this.responses.findValue("content-length"));
          } catch (Exception localException1) {
          }
          if ((this.method.equals("HEAD")) || (l == 0L) || (j == 304) || (j == 204))
          {
            if (this.pi != null) {
              this.pi.finishTracking();
              this.pi = null;
            }
            this.http.finished();
            this.http = null;
            this.inputStream = new EmptyInputStream();
            this.connected = false;
          }

          if ((((j == 200) || (j == 203) || (j == 206) || (j == 300) || (j == 301) || (j == 410))) && 
            (this.cacheHandler != null))
          {
            localObject3 = ParseUtil.toURI(this.url);
            if (localObject3 != null) {
              localObject4 = this;
              if ("https".equalsIgnoreCase(((URI)localObject3).getScheme()))
              {
                try
                {
                  localObject4 = (URLConnection)getClass().getField("httpsURLConnection").get(this);
                }
                catch (IllegalAccessException localIllegalAccessException) {
                }
                catch (NoSuchFieldException localNoSuchFieldException) {
                }
              }
              CacheRequest localCacheRequest = this.cacheHandler.put((URI)localObject3, (URLConnection)localObject4);

              if ((localCacheRequest != null) && (this.http != null)) {
                this.http.setCacheRequest(localCacheRequest);
                this.inputStream = new HttpInputStream(this, this.inputStream, localCacheRequest);
              }
            }

          }

          if (!(this.inputStream instanceof HttpInputStream)) {
            this.inputStream = new HttpInputStream(this, this.inputStream);
          }

          if (j >= 400) {
            if ((j == 404) || (j == 410))
              throw new FileNotFoundException(this.url.toString());

            throw new IOException("Server returned HTTP response code: " + j + " for URL: " + this.url.toString());
          }

          this.poster = null;
          this.strOutputStream = null;
          localObject3 = this.inputStream;

          if (this.proxyAuthKey != null)
            AuthenticationInfo.endAuthRequest(this.proxyAuthKey);
        }
      }
      label1846: throw new ProtocolException("Server redirected too many  times (" + i + ")");
    }
    catch (RuntimeException localRuntimeException)
    {
    }
    catch (IOException localIOException)
    {
      this.rememberedException = localIOException;

      String str1 = this.responses.findValue("Transfer-Encoding");
      if ((this.http != null) && (this.http.isKeepingAlive()) && (enableESBuffer));
      throw localIOException;
    } finally {
      if (this.proxyAuthKey != null)
        AuthenticationInfo.endAuthRequest(this.proxyAuthKey);

      if (this.serverAuthKey != null)
        AuthenticationInfo.endAuthRequest(this.serverAuthKey);
    }
  }

  private IOException getChainedException(IOException paramIOException)
  {
    try
    {
      Object[] arrayOfObject = { paramIOException.getMessage() };
      IOException localIOException = (IOException)AccessController.doPrivileged(new 6(this, paramIOException, arrayOfObject));

      localIOException.initCause(paramIOException);
      return localIOException; } catch (Exception localException) {
    }
    return paramIOException;
  }

  public InputStream getErrorStream()
  {
    if ((this.connected) && (this.responseCode >= 400))
    {
      if (this.errorStream != null)
        return this.errorStream;
      if (this.inputStream != null)
        return this.inputStream;
    }

    return null;
  }

  private AuthenticationInfo resetProxyAuthentication(AuthenticationInfo paramAuthenticationInfo, AuthenticationHeader paramAuthenticationHeader)
    throws IOException
  {
    if ((paramAuthenticationInfo != null) && (paramAuthenticationInfo.getAuthScheme() != AuthScheme.NTLM))
    {
      String str1 = paramAuthenticationHeader.raw();
      if (paramAuthenticationInfo.isAuthorizationStale(str1))
      {
        String str2;
        if (paramAuthenticationInfo instanceof DigestAuthentication) {
          DigestAuthentication localDigestAuthentication = (DigestAuthentication)paramAuthenticationInfo;

          if (tunnelState() == TunnelState.SETUP)
            str2 = localDigestAuthentication.getHeaderValue(connectRequestURI(this.url), HTTP_CONNECT);
          else
            str2 = localDigestAuthentication.getHeaderValue(getRequestURI(), this.method);
        }
        else {
          str2 = paramAuthenticationInfo.getHeaderValue(this.url, this.method);
        }
        this.requests.set(paramAuthenticationInfo.getHeaderName(), str2);
        this.currentProxyCredentials = paramAuthenticationInfo;
        return paramAuthenticationInfo;
      }
      paramAuthenticationInfo.removeFromCache();
    }

    paramAuthenticationInfo = getHttpProxyAuthentication(paramAuthenticationHeader);
    this.currentProxyCredentials = paramAuthenticationInfo;
    return paramAuthenticationInfo;
  }

  TunnelState tunnelState()
  {
    return this.tunnelState;
  }

  public void setTunnelState(TunnelState paramTunnelState)
  {
    this.tunnelState = paramTunnelState;
  }

  public synchronized void doTunneling()
    throws IOException
  {
    int i = 0;
    String str1 = "";
    int j = 0;
    AuthenticationInfo localAuthenticationInfo = null;
    String str2 = null;
    int k = -1;

    MessageHeader localMessageHeader = this.requests;
    this.requests = new MessageHeader();

    int l = 0;
    try
    {
      setTunnelState(TunnelState.SETUP);
      do
      {
        if (!(checkReuseConnection())) {
          proxiedConnect(this.url, str2, k, false);
        }

        sendCONNECTRequest();
        this.responses.reset();

        this.http.parseHTTP(this.responses, null, this);

        if (logger.isLoggable(500)) {
          logger.fine(this.responses.toString());
        }

        if ((this.responses.filterNTLMResponses("Proxy-Authenticate")) && 
          (logger.isLoggable(500))) {
          logger.fine(">>>> Headers are filtered");
          logger.fine(this.responses.toString());
        }

        str1 = this.responses.getValue(0);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1);
        localStringTokenizer.nextToken();
        j = Integer.parseInt(localStringTokenizer.nextToken().trim());
        if (j == 407)
        {
          boolean bool = false;
          Iterator localIterator = this.responses.multiValueIterator("Proxy-Authenticate");
          while (localIterator.hasNext()) {
            localObject1 = ((String)localIterator.next()).trim();
            if ((((String)localObject1).equalsIgnoreCase("Negotiate")) || (((String)localObject1).equalsIgnoreCase("Kerberos")))
            {
              if (l == 0) {
                l = 1; break;
              }
              bool = true;
              this.doingNTLMp2ndStage = false;
              localAuthenticationInfo = null;

              break;
            }
          }

          Object localObject1 = new AuthenticationHeader("Proxy-Authenticate", this.responses, new HttpCallerInfo(this.url, this.http.getProxyHostUsed(), this.http.getProxyPortUsed()), bool);

          if (!(this.doingNTLMp2ndStage)) {
            localAuthenticationInfo = resetProxyAuthentication(localAuthenticationInfo, (AuthenticationHeader)localObject1);

            if (localAuthenticationInfo != null) {
              str2 = this.http.getProxyHostUsed();
              k = this.http.getProxyPortUsed();
              disconnectInternal();
              ++i;
            }
          }
          else {
            String str3 = this.responses.findValue("Proxy-Authenticate");
            reset();
            if (!(localAuthenticationInfo.setHeaders(this, ((AuthenticationHeader)localObject1).headerParser(), str3)))
            {
              disconnectInternal();
              throw new IOException("Authentication failure");
            }
            this.authObj = null;
            this.doingNTLMp2ndStage = false;
          }
        }
        else
        {
          if (localAuthenticationInfo != null)
          {
            localAuthenticationInfo.addToCache();
          }

          if (j == 200) {
            setTunnelState(TunnelState.TUNNELING);
            break;
          }

          disconnectInternal();
          setTunnelState(TunnelState.NONE);
          break; } }
      while (i < maxRedirects);

      if ((i >= maxRedirects) || (j != 200))
        throw new IOException("Unable to tunnel through proxy. Proxy returns \"" + str1 + "\"");

    }
    finally
    {
      if (this.proxyAuthKey != null) {
        AuthenticationInfo.endAuthRequest(this.proxyAuthKey);
      }

    }

    this.requests = localMessageHeader;

    this.responses.reset();
  }

  static String connectRequestURI(URL paramURL) {
    String str = paramURL.getHost();
    int i = paramURL.getPort();
    i = (i != -1) ? i : paramURL.getDefaultPort();

    return str + ":" + i;
  }

  private void sendCONNECTRequest()
    throws IOException
  {
    int i = this.url.getPort();

    this.requests.set(0, HTTP_CONNECT + " " + connectRequestURI(this.url) + " " + "HTTP/1.1", null);

    this.requests.setIfNotSet("User-Agent", userAgent);

    String str = this.url.getHost();
    if ((i != -1) && (i != this.url.getDefaultPort()))
      str = str + ":" + String.valueOf(i);

    this.requests.setIfNotSet("Host", str);

    this.requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");

    if (this.http.getHttpKeepAliveSet()) {
      this.requests.setIfNotSet("Proxy-Connection", "keep-alive");
    }

    setPreemptiveProxyAuthentication(this.requests);

    if (logger.isLoggable(500)) {
      logger.fine(this.requests.toString());
    }

    this.http.writeRequests(this.requests, null);
  }

  private void setPreemptiveProxyAuthentication(MessageHeader paramMessageHeader)
    throws IOException
  {
    AuthenticationInfo localAuthenticationInfo = AuthenticationInfo.getProxyAuth(this.http.getProxyHostUsed(), this.http.getProxyPortUsed());

    if ((localAuthenticationInfo != null) && (localAuthenticationInfo.supportsPreemptiveAuthorization()))
    {
      String str;
      if (localAuthenticationInfo instanceof DigestAuthentication) {
        DigestAuthentication localDigestAuthentication = (DigestAuthentication)localAuthenticationInfo;
        if (tunnelState() == TunnelState.SETUP) {
          str = localDigestAuthentication.getHeaderValue(connectRequestURI(this.url), HTTP_CONNECT);
        }
        else
          str = localDigestAuthentication.getHeaderValue(getRequestURI(), this.method);
      }
      else {
        str = localAuthenticationInfo.getHeaderValue(this.url, this.method);
      }

      paramMessageHeader.set(localAuthenticationInfo.getHeaderName(), str);
      this.currentProxyCredentials = localAuthenticationInfo;
    }
  }

  private AuthenticationInfo getHttpProxyAuthentication(AuthenticationHeader paramAuthenticationHeader)
  {
    Object localObject1 = null;
    String str1 = paramAuthenticationHeader.raw();
    String str2 = this.http.getProxyHostUsed();
    int i = this.http.getProxyPortUsed();
    if ((str2 != null) && (paramAuthenticationHeader.isPresent())) {
      Object localObject2;
      Object localObject3;
      HeaderParser localHeaderParser = paramAuthenticationHeader.headerParser();
      String str3 = localHeaderParser.findValue("realm");
      String str4 = paramAuthenticationHeader.scheme();
      AuthScheme localAuthScheme = AuthScheme.UNKNOWN;
      if ("basic".equalsIgnoreCase(str4)) {
        localAuthScheme = AuthScheme.BASIC;
      } else if ("digest".equalsIgnoreCase(str4)) {
        localAuthScheme = AuthScheme.DIGEST;
      } else if ("ntlm".equalsIgnoreCase(str4)) {
        localAuthScheme = AuthScheme.NTLM;
        this.doingNTLMp2ndStage = true;
      } else if ("Kerberos".equalsIgnoreCase(str4)) {
        localAuthScheme = AuthScheme.KERBEROS;
        this.doingNTLMp2ndStage = true;
      } else if ("Negotiate".equalsIgnoreCase(str4)) {
        localAuthScheme = AuthScheme.NEGOTIATE;
        this.doingNTLMp2ndStage = true;
      }

      if (str3 == null)
        str3 = "";
      this.proxyAuthKey = AuthenticationInfo.getProxyAuthKey(str2, i, str3, localAuthScheme);
      localObject1 = AuthenticationInfo.getProxyAuth(this.proxyAuthKey);
      if (localObject1 == null) {
        switch (8.$SwitchMap$sun$net$www$protocol$http$AuthScheme[localAuthScheme.ordinal()])
        {
        case 1:
          localObject2 = null;
          try {
            String str5 = str2;
            localObject2 = (InetAddress)AccessController.doPrivileged(new 7(this, str5));
          }
          catch (PrivilegedActionException localPrivilegedActionException)
          {
          }

          localObject3 = privilegedRequestPasswordAuthentication(str2, (InetAddress)localObject2, i, "http", str3, str4, this.url, Authenticator.RequestorType.PROXY);

          if (localObject3 != null)
            localObject1 = new BasicAuthentication(true, str2, i, str3, (PasswordAuthentication)localObject3); break;
        case 2:
          localObject3 = privilegedRequestPasswordAuthentication(str2, null, i, this.url.getProtocol(), str3, str4, this.url, Authenticator.RequestorType.PROXY);

          if (localObject3 != null) {
            DigestAuthentication.Parameters localParameters = new DigestAuthentication.Parameters();

            localObject1 = new DigestAuthentication(true, str2, i, str3, str4, (PasswordAuthentication)localObject3, localParameters);
          }
          break;
        case 3:
          if (NTLMAuthenticationProxy.supported)
          {
            if (this.tryTransparentNTLMProxy) {
              this.tryTransparentNTLMProxy = NTLMAuthenticationProxy.supportsTransparentAuth;
            }

            localObject3 = null;
            if (this.tryTransparentNTLMProxy)
              logger.finest("Trying Transparent NTLM authentication");
            else {
              localObject3 = privilegedRequestPasswordAuthentication(str2, null, i, this.url.getProtocol(), "", str4, this.url, Authenticator.RequestorType.PROXY);
            }

            if ((this.tryTransparentNTLMProxy) || ((!(this.tryTransparentNTLMProxy)) && (localObject3 != null)))
            {
              localObject1 = NTLMAuthenticationProxy.proxy.create(true, str2, i, (PasswordAuthentication)localObject3);
            }

            this.tryTransparentNTLMProxy = false; } break;
        case 4:
          localObject1 = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Negotiate"));
          break;
        case 5:
          localObject1 = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Kerberos"));
          break;
        case 6:
          logger.finest("Unknown/Unsupported authentication scheme: " + str4);
        default:
          throw new AssertionError("should not reach here");
        }

      }

      if ((localObject1 == null) && (defaultAuth != null) && (defaultAuth.schemeSupported(str4)))
        try
        {
          localObject2 = new URL("http", str2, i, "/");
          localObject3 = defaultAuth.authString((URL)localObject2, str4, str3);
          if (localObject3 != null)
            localObject1 = new BasicAuthentication(true, str2, i, str3, (String)localObject3);
        }
        catch (MalformedURLException localMalformedURLException)
        {
        }

      if ((localObject1 != null) && 
        (!(((AuthenticationInfo)localObject1).setHeaders(this, localHeaderParser, str1))))
        localObject1 = null;

    }

    if (logger.isLoggable(400))
      logger.finer("Proxy Authentication for " + paramAuthenticationHeader.toString() + " returned " + ((localObject1 != null) ? localObject1.toString() : "null"));

    return ((AuthenticationInfo)(AuthenticationInfo)(AuthenticationInfo)localObject1);
  }

  private AuthenticationInfo getServerAuthentication(AuthenticationHeader paramAuthenticationHeader)
  {
    Object localObject1 = null;
    String str1 = paramAuthenticationHeader.raw();

    if (paramAuthenticationHeader.isPresent()) {
      Object localObject2;
      HeaderParser localHeaderParser = paramAuthenticationHeader.headerParser();
      String str2 = localHeaderParser.findValue("realm");
      String str3 = paramAuthenticationHeader.scheme();
      AuthScheme localAuthScheme = AuthScheme.UNKNOWN;
      if ("basic".equalsIgnoreCase(str3)) {
        localAuthScheme = AuthScheme.BASIC;
      } else if ("digest".equalsIgnoreCase(str3)) {
        localAuthScheme = AuthScheme.DIGEST;
      } else if ("ntlm".equalsIgnoreCase(str3)) {
        localAuthScheme = AuthScheme.NTLM;
        this.doingNTLM2ndStage = true;
      } else if ("Kerberos".equalsIgnoreCase(str3)) {
        localAuthScheme = AuthScheme.KERBEROS;
        this.doingNTLM2ndStage = true;
      } else if ("Negotiate".equalsIgnoreCase(str3)) {
        localAuthScheme = AuthScheme.NEGOTIATE;
        this.doingNTLM2ndStage = true;
      }

      this.domain = localHeaderParser.findValue("domain");
      if (str2 == null)
        str2 = "";
      this.serverAuthKey = AuthenticationInfo.getServerAuthKey(this.url, str2, localAuthScheme);
      localObject1 = AuthenticationInfo.getServerAuth(this.serverAuthKey);
      InetAddress localInetAddress = null;
      if (localObject1 == null)
        try {
          localInetAddress = InetAddress.getByName(this.url.getHost());
        }
        catch (UnknownHostException localUnknownHostException)
        {
        }

      int i = this.url.getPort();
      if (i == -1)
        i = this.url.getDefaultPort();

      if (localObject1 == null) {
        switch (8.$SwitchMap$sun$net$www$protocol$http$AuthScheme[localAuthScheme.ordinal()])
        {
        case 5:
          localObject1 = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Kerberos"));
          break;
        case 4:
          localObject1 = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Negotiate"));
          break;
        case 1:
          localObject2 = privilegedRequestPasswordAuthentication(this.url.getHost(), localInetAddress, i, this.url.getProtocol(), str2, str3, this.url, Authenticator.RequestorType.SERVER);

          if (localObject2 != null)
            localObject1 = new BasicAuthentication(false, this.url, str2, (PasswordAuthentication)localObject2); break;
        case 2:
          localObject2 = privilegedRequestPasswordAuthentication(this.url.getHost(), localInetAddress, i, this.url.getProtocol(), str2, str3, this.url, Authenticator.RequestorType.SERVER);

          if (localObject2 != null) {
            this.digestparams = new DigestAuthentication.Parameters();
            localObject1 = new DigestAuthentication(false, this.url, str2, str3, (PasswordAuthentication)localObject2, this.digestparams); } break;
        case 3:
          if (NTLMAuthenticationProxy.supported) {
            URL localURL;
            try {
              localURL = new URL(this.url, "/");
            } catch (Exception localException) {
              localURL = this.url;
            }

            if (this.tryTransparentNTLMServer) {
              this.tryTransparentNTLMServer = NTLMAuthenticationProxy.supportsTransparentAuth;

              if (this.tryTransparentNTLMServer)
                this.tryTransparentNTLMServer = NTLMAuthenticationProxy.isTrustedSite(this.url);

            }

            localObject2 = null;
            if (this.tryTransparentNTLMServer)
              logger.finest("Trying Transparent NTLM authentication");
            else {
              localObject2 = privilegedRequestPasswordAuthentication(this.url.getHost(), localInetAddress, i, this.url.getProtocol(), "", str3, this.url, Authenticator.RequestorType.SERVER);
            }

            if ((this.tryTransparentNTLMServer) || ((!(this.tryTransparentNTLMServer)) && (localObject2 != null)))
            {
              localObject1 = NTLMAuthenticationProxy.proxy.create(false, localURL, (PasswordAuthentication)localObject2);
            }

            this.tryTransparentNTLMServer = false; }
          break;
        case 6:
          logger.finest("Unknown/Unsupported authentication scheme: " + str3);
        default:
          throw new AssertionError("should not reach here");
        }

      }

      if ((localObject1 == null) && (defaultAuth != null) && (defaultAuth.schemeSupported(str3)))
      {
        localObject2 = defaultAuth.authString(this.url, str3, str2);
        if (localObject2 != null) {
          localObject1 = new BasicAuthentication(false, this.url, str2, (String)localObject2);
        }

      }

      if ((localObject1 != null) && 
        (!(((AuthenticationInfo)localObject1).setHeaders(this, localHeaderParser, str1))))
        localObject1 = null;

    }

    if (logger.isLoggable(400))
      logger.finer("Server Authentication for " + paramAuthenticationHeader.toString() + " returned " + ((localObject1 != null) ? localObject1.toString() : "null"));

    return ((AuthenticationInfo)(AuthenticationInfo)localObject1);
  }

  private void checkResponseCredentials(boolean paramBoolean)
    throws IOException
  {
    try
    {
      String str;
      DigestAuthentication localDigestAuthentication;
      if (!(this.needToCheck))
        return;
      if ((validateProxy) && (this.currentProxyCredentials != null) && (this.currentProxyCredentials instanceof DigestAuthentication))
      {
        str = this.responses.findValue("Proxy-Authentication-Info");
        if ((paramBoolean) || (str != null)) {
          localDigestAuthentication = (DigestAuthentication)this.currentProxyCredentials;

          localDigestAuthentication.checkResponse(str, this.method, getRequestURI());
          this.currentProxyCredentials = null;
        }
      }
      if ((validateServer) && (this.currentServerCredentials != null) && (this.currentServerCredentials instanceof DigestAuthentication))
      {
        str = this.responses.findValue("Authentication-Info");
        if ((paramBoolean) || (str != null)) {
          localDigestAuthentication = (DigestAuthentication)this.currentServerCredentials;

          localDigestAuthentication.checkResponse(str, this.method, this.url);
          this.currentServerCredentials = null;
        }
      }
      if ((this.currentServerCredentials == null) && (this.currentProxyCredentials == null))
        this.needToCheck = false;
    }
    catch (IOException localIOException) {
      disconnectInternal();
      this.connected = false;
      throw localIOException;
    }
  }

  String getRequestURI()
    throws IOException
  {
    if (this.requestURI == null)
      this.requestURI = this.http.getURLFile();

    return this.requestURI;
  }

  private boolean followRedirect()
    throws IOException
  {
    URL localURL;
    if (!(getInstanceFollowRedirects())) {
      return false;
    }

    int i = getResponseCode();
    if ((i < 300) || (i > 307) || (i == 306) || (i == 304))
    {
      return false;
    }
    String str1 = getHeaderField("Location");
    if (str1 == null)
    {
      return false;
    }
    try
    {
      localURL = new URL(str1);
      if (!(this.url.getProtocol().equalsIgnoreCase(localURL.getProtocol())))
        return false;

    }
    catch (MalformedURLException localMalformedURLException)
    {
      localURL = new URL(this.url, str1);
    }
    disconnectInternal();
    if (streaming())
      throw new HttpRetryException("cannot retry due to redirection, in streaming mode", i, str1);

    if (logger.isLoggable(500)) {
      logger.fine("Redirected from " + this.url + " to " + localURL);
    }

    this.responses = new MessageHeader();
    if (i == 305)
    {
      String str2 = localURL.getHost();
      int k = localURL.getPort();

      SecurityManager localSecurityManager = System.getSecurityManager();
      if (localSecurityManager != null) {
        localSecurityManager.checkConnect(str2, k);
      }

      setProxiedClient(this.url, str2, k);
      this.requests.set(0, this.method + " " + getRequestURI() + " " + "HTTP/1.1", null);

      this.connected = true;
    }
    else
    {
      this.url = localURL;
      this.requestURI = null;
      if ((this.method.equals("POST")) && (!(Boolean.getBoolean("http.strictPostRedirect"))) && (i != 307))
      {
        this.requests = new MessageHeader();
        this.setRequests = false;
        setRequestMethod("GET");
        this.poster = null;
        if (!(checkReuseConnection()))
          connect();
      } else {
        if (!(checkReuseConnection())) {
          connect();
        }

        if (this.http != null) {
          this.requests.set(0, this.method + " " + getRequestURI() + " " + "HTTP/1.1", null);

          int j = this.url.getPort();
          String str3 = this.url.getHost();
          if ((j != -1) && (j != this.url.getDefaultPort()))
            str3 = str3 + ":" + String.valueOf(j);

          this.requests.set("Host", str3);
        }
      }
    }
    return true;
  }

  private void reset()
    throws IOException
  {
    this.http.reuse = true;

    this.reuseClient = this.http;
    InputStream localInputStream = this.http.getInputStream();
    if (!(this.method.equals("HEAD")))
    {
      int i;
      long l2;
      try
      {
        if ((localInputStream instanceof ChunkedInputStream) || (localInputStream instanceof MeteredStream))
        {
          while (true)
            if (localInputStream.read(this.cdata) <= 0)
              break label138;

        }

        long l1 = 0L;
        i = 0;
        String str = this.responses.findValue("Content-Length");
        if (str != null)
          try {
            l1 = Long.parseLong(str);
          } catch (NumberFormatException localNumberFormatException) {
            l1 = 0L;
          }

        for (l2 = 0L; l2 < l1; ) {
          if ((i = localInputStream.read(this.cdata)) == -1)
            break;

          label138: l2 += i;
        }
      }
      catch (IOException localIOException1)
      {
        this.http.reuse = false;
        this.reuseClient = null;
        disconnectInternal();
        return;
      }
      try {
        if (localInputStream instanceof MeteredStream)
          localInputStream.close();
      } catch (IOException localIOException2) {
      }
    }
    this.responseCode = -1;
    this.responses = new MessageHeader();
    this.connected = false;
  }

  private void disconnectWeb()
    throws IOException
  {
    if ((usingProxy()) && (this.http.isKeepingAlive())) {
      this.responseCode = -1;

      reset();
    } else {
      disconnectInternal();
    }
  }

  private void disconnectInternal()
  {
    this.responseCode = -1;
    this.inputStream = null;
    if (this.pi != null) {
      this.pi.finishTracking();
      this.pi = null;
    }
    if (this.http != null) {
      this.http.closeServer();
      this.http = null;
      this.connected = false;
    }
  }

  public void disconnect()
  {
    this.responseCode = -1;
    if (this.pi != null) {
      this.pi.finishTracking();
      this.pi = null;
    }

    if (this.http != null)
    {
      if (this.inputStream != null) {
        HttpClient localHttpClient = this.http;

        boolean bool = localHttpClient.isKeepingAlive();
        try
        {
          this.inputStream.close();
        }
        catch (IOException localIOException)
        {
        }

        if (bool) {
          localHttpClient.closeIdleConnection();
        }

      }
      else
      {
        this.http.setDoNotRetry(true);

        this.http.closeServer();
      }

      this.http = null;
      this.connected = false;
    }
    this.cachedInputStream = null;
    if (this.cachedHeaders != null)
      this.cachedHeaders.reset();
  }

  public boolean usingProxy()
  {
    if (this.http != null)
      return (this.http.getProxyHostUsed() != null);

    return false;
  }

  private String filterHeaderField(String paramString1, String paramString2)
  {
    if (paramString2 == null)
      return null;

    if (("set-cookie".equalsIgnoreCase(paramString1)) || ("set-cookie2".equalsIgnoreCase(paramString1)))
    {
      if (this.cookieHandler == null)
        return paramString2;

      JavaNetHttpCookieAccess localJavaNetHttpCookieAccess = SharedSecrets.getJavaNetHttpCookieAccess();

      StringBuilder localStringBuilder = new StringBuilder();
      List localList = localJavaNetHttpCookieAccess.parse(paramString2);
      int i = 0;
      Iterator localIterator = localList.iterator();
      while (true) { HttpCookie localHttpCookie;
        while (true) { if (!(localIterator.hasNext())) break label135; localHttpCookie = (HttpCookie)localIterator.next();

          if (!(localHttpCookie.isHttpOnly())) break;
        }
        if (i != 0)
          localStringBuilder.append(',');
        localStringBuilder.append(localJavaNetHttpCookieAccess.header(localHttpCookie));
        i = 1;
      }

      label135: return ((localStringBuilder.length() == 0) ? "" : localStringBuilder.toString());
    }

    return paramString2;
  }

  private java.util.Map<String, List<String>> getFilteredHeaderFields()
  {
    java.util.Map localMap;
    if (this.filteredHeaders != null)
      return this.filteredHeaders;

    HashMap localHashMap = new HashMap();

    if (this.cachedHeaders != null)
      localMap = this.cachedHeaders.getHeaders();
    else
      localMap = this.responses.getHeaders();

    for (Map.Entry localEntry : localMap.entrySet()) {
      String str1 = (String)localEntry.getKey();
      List localList = (List)localEntry.getValue(); ArrayList localArrayList = new ArrayList();
      for (String str2 : localList) {
        String str3 = filterHeaderField(str1, str2);
        if (str3 != null)
          localArrayList.add(str3);
      }
      if (!(localArrayList.isEmpty()))
        localHashMap.put(str1, Collections.unmodifiableList(localArrayList));
    }

    return (this.filteredHeaders = Collections.unmodifiableMap(localHashMap));
  }

  public String getHeaderField(String paramString)
  {
    try
    {
      getInputStream();
    } catch (IOException localIOException) {
    }
    if (this.cachedHeaders != null) {
      return filterHeaderField(paramString, this.cachedHeaders.findValue(paramString));
    }

    return filterHeaderField(paramString, this.responses.findValue(paramString));
  }

  public java.util.Map<String, List<String>> getHeaderFields()
  {
    try
    {
      getInputStream();
    } catch (IOException localIOException) {
    }
    return getFilteredHeaderFields();
  }

  public String getHeaderField(int paramInt)
  {
    try
    {
      getInputStream();
    } catch (IOException localIOException) {
    }
    if (this.cachedHeaders != null) {
      return filterHeaderField(this.cachedHeaders.getKey(paramInt), this.cachedHeaders.getValue(paramInt));
    }

    return filterHeaderField(this.responses.getKey(paramInt), this.responses.getValue(paramInt));
  }

  public String getHeaderFieldKey(int paramInt)
  {
    try
    {
      getInputStream();
    } catch (IOException localIOException) {
    }
    if (this.cachedHeaders != null) {
      return this.cachedHeaders.getKey(paramInt);
    }

    return this.responses.getKey(paramInt);
  }

  public void setRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected)
      throw new IllegalStateException("Already connected");
    if (paramString1 == null)
      throw new NullPointerException("key is null");

    if (isExternalMessageHeaderAllowed(paramString1, paramString2))
      this.requests.set(paramString1, paramString2);
  }

  public void addRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected)
      throw new IllegalStateException("Already connected");
    if (paramString1 == null)
      throw new NullPointerException("key is null");

    if (isExternalMessageHeaderAllowed(paramString1, paramString2))
      this.requests.add(paramString1, paramString2);
  }

  public void setAuthenticationProperty(String paramString1, String paramString2)
  {
    checkMessageHeader(paramString1, paramString2);
    this.requests.set(paramString1, paramString2);
  }

  public synchronized String getRequestProperty(String paramString)
  {
    if (paramString == null) {
      return null;
    }

    for (int i = 0; i < EXCLUDE_HEADERS.length; ++i)
      if (paramString.equalsIgnoreCase(EXCLUDE_HEADERS[i]))
        return null;


    if (!(this.setUserCookies)) {
      if (paramString.equalsIgnoreCase("Cookie"))
        return this.userCookies;

      if (paramString.equalsIgnoreCase("Cookie2"))
        return this.userCookies2;
    }

    return this.requests.findValue(paramString);
  }

  public synchronized java.util.Map<String, List<String>> getRequestProperties()
  {
    if (this.connected) {
      throw new IllegalStateException("Already connected");
    }

    if (this.setUserCookies) {
      return this.requests.getHeaders(EXCLUDE_HEADERS);
    }

    HashMap localHashMap = null;
    if ((this.userCookies != null) || (this.userCookies2 != null)) {
      localHashMap = new HashMap();
      if (this.userCookies != null)
        localHashMap.put("Cookie", this.userCookies);

      if (this.userCookies2 != null)
        localHashMap.put("Cookie2", this.userCookies2);
    }

    return this.requests.filterAndAddHeaders(EXCLUDE_HEADERS2, localHashMap);
  }

  public void setConnectTimeout(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("timeouts can't be negative");
    this.connectTimeout = paramInt;
  }

  public int getConnectTimeout()
  {
    return ((this.connectTimeout < 0) ? 0 : this.connectTimeout);
  }

  public void setReadTimeout(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("timeouts can't be negative");
    this.readTimeout = paramInt;
  }

  public int getReadTimeout()
  {
    return ((this.readTimeout < 0) ? 0 : this.readTimeout);
  }

  public CookieHandler getCookieHandler() {
    return this.cookieHandler;
  }

  String getMethod() {
    return this.method;
  }

  private MessageHeader mapToMessageHeader(java.util.Map<String, List<String>> paramMap) {
    String str1;
    MessageHeader localMessageHeader = new MessageHeader();
    if ((paramMap == null) || (paramMap.isEmpty()))
      return localMessageHeader;

    for (Map.Entry localEntry : paramMap.entrySet()) {
      str1 = (String)localEntry.getKey();
      List localList = (List)localEntry.getValue();
      for (String str2 : localList)
        if (str1 == null)
          localMessageHeader.prepend(str1, str2);
        else
          localMessageHeader.add(str1, str2);

    }

    return localMessageHeader;
  }

  static
  {
    int i;
    HTTP_CONNECT = "CONNECT";

    enableESBuffer = false;

    timeout4ESBuffer = 0;

    bufSize4ES = 0;

    restrictedHeaders = new String[] { "Access-Control-Request-Headers", "Access-Control-Request-Method", "Connection", "Content-Length", "Content-Transfer-Encoding", "Host", "Keep-Alive", "Origin", "Trailer", "Transfer-Encoding", "Upgrade", "Via" };

    maxRedirects = ((Integer)AccessController.doPrivileged(new GetIntegerAction("http.maxRedirects", 20))).intValue();

    version = (String)AccessController.doPrivileged(new GetPropertyAction("java.version"));

    String str = (String)AccessController.doPrivileged(new GetPropertyAction("http.agent"));

    if (str == null)
      str = "Java/" + version;
    else
      str = str + " Java/" + version;

    userAgent = str;
    validateProxy = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("http.auth.digest.validateProxy"))).booleanValue();

    validateServer = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("http.auth.digest.validateServer"))).booleanValue();

    enableESBuffer = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.net.http.errorstream.enableBuffering"))).booleanValue();

    timeout4ESBuffer = ((Integer)AccessController.doPrivileged(new GetIntegerAction("sun.net.http.errorstream.timeout", 300))).intValue();

    if (timeout4ESBuffer <= 0) {
      timeout4ESBuffer = 300;
    }

    bufSize4ES = ((Integer)AccessController.doPrivileged(new GetIntegerAction("sun.net.http.errorstream.bufferSize", 4096))).intValue();

    if (bufSize4ES <= 0) {
      bufSize4ES = 4096;
    }

    allowRestrictedHeaders = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.net.http.allowRestrictedHeaders"))).booleanValue();

    if (!(allowRestrictedHeaders)) {
      restrictedHeaderSet = new HashSet(restrictedHeaders.length);
      for (i = 0; i < restrictedHeaders.length; ++i)
        restrictedHeaderSet.add(restrictedHeaders[i].toLowerCase());
    }
    else {
      restrictedHeaderSet = null;
    }

    EXCLUDE_HEADERS = new String[] { "Proxy-Authorization", "Authorization" };

    EXCLUDE_HEADERS2 = new String[] { "Proxy-Authorization", "Authorization", "Cookie", "Cookie2" };

    logger = PlatformLogger.getLogger("sun.net.www.protocol.http.HttpURLConnection");
  }
}