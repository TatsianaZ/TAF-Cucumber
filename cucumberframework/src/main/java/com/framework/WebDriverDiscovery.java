package com.framework;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class WebDriverDiscovery extends EventFiringWebDriver {

	protected static final Logger LOG = LoggerFactory.getLogger(WebDriverDiscovery.class);

	private static RemoteWebDriver remoteWebDriver;
	private static String operatingSystem;
	private static String os_Version;
	private static String browserName;
	private static String browserVersion;
	private static Properties props;

	private static final Map<String, String> browserMap = Collections.unmodifiableMap(new HashMap<String, String>() {
		{
			System.out.println("browserMap");
			put("chrome", "org.openqa.selenium.chrome.ChromeDriver");
			put("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
			put("safari", "org.openqa.selenium.safari.SafariDriver");
			put("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
		}
	});

	private static final Thread CLOSE_THREAD = new Thread() {

		@Override
		public void run() {
			try {
				remoteWebDriver.quit();
			} catch (Throwable ube) {
			}
		}
	};

	public static String getOperatingSystem() {
		return operatingSystem;
	}

	public static String getOs_Version() {
		return os_Version;
	}

	public static String getBrowserVersion() {
		return browserVersion;
	}

	public static String getBrowserName() {
		return browserName;
	}

	static {
		String driverPath = System.getProperty("basedir");
		System.out.println("driverPath: " + driverPath);

		String driverType = System.getProperty("driverType");
		if (StringUtils.isEmpty(driverType)) {
			driverType = "firefox";
			LOG.warn("Empty value of driverType system property");
		}
		LOG.info("DRIVER_TYPE: " + driverType);
		System.out.println("driverType: " + driverType);

		String localBrowserType = browserMap.get(driverType);

		System.out.println("after get method");

		if (null != (localBrowserType)) {
			System.out.println("111");
			Class<?> retrievedClass = null;
			try {
				retrievedClass = Class.forName(localBrowserType);
				System.out.println("retrievedClass");
			} catch (ClassNotFoundException e) {
			}
			if (retrievedClass.getSuperclass().equals(RemoteWebDriver.class)) {
				try {
					System.out.println("11111111111");
					remoteWebDriver = (RemoteWebDriver) retrievedClass.newInstance();

					if(driverType.equals("chrome")){
						ChromeOptions options = new ChromeOptions();
						options.addArguments("--disable-features=VizDisplayCompositor");
						WebDriver driver = new ChromeDriver(options);
						driver.manage().window().maximize();
						driver.get("https://google.com");
						System.out.println("after get");
						driver.quit();
					}

					System.out.println("22222222222");
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				}
				System.out.println("3333333333333");
				remoteWebDriver.manage().window().maximize();
				System.out.println("444444444444");
			} else {
				throw new IllegalArgumentException("driverType must extend");
			}
		}
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	public WebDriverDiscovery() {
		super(remoteWebDriver);
	}

	public RemoteWebDriver getRemoteWebDriver() {
		return remoteWebDriver;
	}

	public void close() {
		remoteWebDriver.quit();
	}

	public String getBrowserCookiesAsString() {
		return getBrowserCookiesAsString(null);
	}

	public String getBrowserCookiesAsStringWithoutTS77Cookie() {
		Set<Cookie> ignoredCookies = new HashSet<>();
		Cookie ignoredTS77Cookie = new Cookie("_77", "");
		ignoredCookies.add(ignoredTS77Cookie);
		return getBrowserCookiesAsString(ignoredCookies);
	}

	public String getBrowserCookiesAsString(Set<Cookie> ignoredCookies) {
		Set<Cookie> cookies = removeCookiesFrom(remoteWebDriver.manage().getCookies(), ignoredCookies);
		StringBuilder sb = new StringBuilder();
		int i = 1;
		int cookiesCount = cookies.size();
		for (Cookie cookie : cookies) {
			sb.append(cookie.getName()).append("=").append(cookie.getValue());
			if (i < cookiesCount) {
				sb.append("; ");
			}
			i++;
		}
		return sb.toString();
	}

	public void goBack() {
		for (int i = 0; i < 2; i++) {
			remoteWebDriver.navigate().back();
		}
	}

	public String getCurrentRootAddress(boolean withProtocol) {
		String currUrl = remoteWebDriver.getCurrentUrl();
		String[] splitedUrlBySlash = currUrl.split("/");
		return (withProtocol) ? splitedUrlBySlash[0] + "//" + splitedUrlBySlash[2] : splitedUrlBySlash[2];
	}

	private Set<Cookie> removeCookiesFrom(Set<Cookie> sourceCookies, Set<Cookie> cookiesToRemove) {
		if (cookiesToRemove == null) {
			return sourceCookies;
		}
		Set<Cookie> resultCookiesList = new HashSet<>();
		boolean isFound = false;
		for (Cookie sourceCookie : sourceCookies) {
			for (Cookie cookieToRemove : cookiesToRemove) {
				if (sourceCookie.getName().endsWith(cookieToRemove.getName())
						|| sourceCookie.getName().startsWith(cookieToRemove.getName())) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				resultCookiesList.add(sourceCookie);
			}
		}
		return resultCookiesList;
	}
}
