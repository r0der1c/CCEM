package ccesm.util;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import ccesm.config.security.CustomUser;
import ccesm.exception.WebException;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.logging.Logger;

@Component
public class WebUtility {
	public static final String DAO_QUERIES_LOCATION = "dao-queries";
	public static final String JASPER_REPORTS_LOCATION = "jasper-reports";
	public static final String DEFAULT_WEB_ENCODING = "ISO-8859-1";
	public static final Locale LOCALE_ES = new Locale("es_SV");
	public static final String MESSAGE = "message";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";

	public static final SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat dd_MM_yyyy_hh_mm_ss = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	public static final SimpleDateFormat dd_MM_yyyy_HH_mm = new SimpleDateFormat("dd/MM/yyyy HH:mm");

//	private static ReloadableResourceBundleMessageSource propertiesApplication;

	public static List<XmlMarshalledObjectQuery> applicationQueries = null;

//	@Resource
//	private void setPropertiesApplication(ReloadableResourceBundleMessageSource propertiesApplication){
//		WebUtility.propertiesApplication = propertiesApplication;
//	}
//
//	public static String getProperty(String key){
//		return getProperty(key, null);
//	}
//
//	public static String getProperty(String key, Object[] args){
//		return propertiesApplication.getMessage(key, args, WebUtility.LOCALE_ES);
//	}
//
//	public static String getProperty(String messageKey, Object[] args, String defaultMessage){
//		return propertiesApplication.getMessage(messageKey, args, defaultMessage, WebUtility.LOCALE_ES);
//	}

	public static CustomUser getCustomUserFromAcegi() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (obj instanceof CustomUser) return (CustomUser) obj;
		else return null;
	}

	public static Collection<GrantedAuthority> getGrantedAuthorities(){
		return (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}

	public static boolean hasRole(String role) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if(role.equalsIgnoreCase(grantedAuthority.getAuthority())){
				return true;
			}
		}
		return false;
	}

	public static List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}

	public static boolean isEmptyList(List<?> list){
		return list == null || list.isEmpty();
	}

//	public static String getMD5String(String rawPass, String salt) throws WebException {
//		String hashedString = StringUtils.EMPTY;
//		try {
//			hashedString = passwordEncoderMD5.encodePassword(rawPass, salt);
//		}
//		catch (Exception e) {
////			logger.error("Error encriptando cadena", e);
//			throw new WebException("Error encriptando cadena");
//		}
//		return hashedString;
//	}

//	public static String getHashedString(String clave, String usuario) throws WebException {
//		String hashedString = StringUtils.EMPTY;
//		try {
//			hashedString = passwordEncoder.encodePassword(clave, usuario);
//		}
//		catch (Exception e) {
////			logger.error("Error encriptando cadena", e);
//			throw new WebException("Error encriptando cadena");
//		}
//		return hashedString;
//	}

	public static String getQueryByName(String queryName) {
		String query = StringUtils.EMPTY;
//		if (applicationQueries == null)
			applicationQueries = loadQueries(DAO_QUERIES_LOCATION);
		XmlMarshalledObjectQuery objectToFind = new XmlMarshalledObjectQuery();
		objectToFind.setName(queryName);
		int index = Collections.binarySearch(applicationQueries, objectToFind);
		if (index >= 0) query = applicationQueries.get(index).getValue();
		return query;
	}

	private static List<XmlMarshalledObjectQuery> loadQueries(String location) {
		XmlMarshalledObject object = null;
		List<String> xmlQueryFiles = getXmlFiles(location);
		String xmlString = StringUtils.EMPTY;
		List<XmlMarshalledObjectQuery> gestorQueries = new ArrayList<XmlMarshalledObjectQuery>();
		for (String queryFile : xmlQueryFiles) {
			xmlString = getXmlContent(location + "/" + queryFile);
			object = unmarshallXml(xmlString);
			gestorQueries.addAll(object.getQuery());
		}
		Collections.sort(gestorQueries);
		return gestorQueries;
	}

	private static XmlMarshalledObject unmarshallXml(String xmlString) {
		XmlMarshalledObject output = new XmlMarshalledObject();
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(XmlMarshalledObject.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			output = (XmlMarshalledObject) unmarshaller.unmarshal(new StringReader(xmlString));
		}
		catch (JAXBException e) {
//			logger.error("Error convirtiendo objeto a XML: " + e.getMessage(), e);
		}
		return output;
	}

	private static List<String> getXmlFiles(String xmlPathLocation) {
		List<String> files = new ArrayList<String>();
		String url = null;
		try {
			url = getPathFolder(xmlPathLocation);
			File folder = new File(url);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) if (file.isFile()) files.add(file.getName());
		}
		catch (Exception e) {
//			logger.error("Error leyendo directorio: " + e.getMessage(), e);
		}
		return files;
	}

	public static String getPathFolder(String name) {
		try {
			return URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(name).getPath(), "UTF-8");
		}
		catch (Exception e) {
//			logger.error("Error leyendo directorio: " + e.getMessage(), e);
		}
		return "";
	}

	private static String getXmlContent(String xmlFileName) {
		String xmlString = StringUtils.EMPTY;
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFileName);
			xmlString = IOUtils.toString(inputStream, DEFAULT_WEB_ENCODING);
		}
		catch (Exception e) {
//			logger.error("Error leyendo archivo XML: " + e.getMessage(), e);
		}
		return xmlString;
	}

	public static Object parseXml(String xml, Class<?> clazz) throws Exception {
		JAXBContext jbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jbContext.createUnmarshaller();
		Object response = unmarshaller.unmarshal(new StringReader(xml));
		return response;
	}

	public static String serializeObject2Xml(Object object, Class<?> clazz) {
		JAXBContext context = null;
		StringWriter writer = new StringWriter();
		try {
			context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			// // sin encabezado
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(object, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static String nodeToString(Node node){
        Source source = new DOMSource(node);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        try{
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        }
        catch (TransformerException e){
        	e.printStackTrace();
//        	logger.error("Error convirtiendo de Node a String: " + e, e);
        	return StringUtils.EMPTY;
        }
    }

	public static String getElementById(String id, Document document){
		Object result = null;
		try {
			result = XPathFactory.newInstance().newXPath().evaluate("//*[@id='" + id + "']",document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
//			logger.error("Error obteniendo Id de un Document: " + e, e);
			return StringUtils.EMPTY;
		}
		if(result == null || ((NodeList) result).getLength() <= 0) return StringUtils.EMPTY;
		Node node = ((NodeList) result).item(0);
		return nodeToString(node);
	}

	public static String[] getElementByTagName(String tagName, Document document){
		NodeList result = document.getElementsByTagName(tagName);
		if(result == null || ((NodeList) result).getLength() <= 0){
			String[] vacio = {""};
			return vacio;
		}
		ArrayList<String> nodos = new ArrayList<String>();
		for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            nodos.add(nodeToString(node));
		}
		return nodos.toArray(new String[nodos.size()]);
	}

	public static String getJasperLocation(String reportName) {
		String path = "";
		try {
			path = getPathFolder(JASPER_REPORTS_LOCATION + "/" + reportName);
		} catch (Exception e) {
//			logger.error("Error Obteniendo Ubicacion del Archivo Jasper: " + e.getMessage(), e);
		}
		return path;
	}

	public static String toJson(Object object){
		return toJson(object, new GsonBuilder().setDateFormat(dd_MM_yyyy.toPattern()));
	}

	public static String toJson(Object object, GsonBuilder gsonBuilder){
		return gsonBuilder.create().toJson(object);
	}

	public static String toJson(Object object, String dateFormat){
        return toJson(object, new GsonBuilder().setDateFormat(dateFormat));
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, new GsonBuilder().setDateFormat(dd_MM_yyyy.toPattern()));
	}

	public static <T> T fromJson(String json, Type type) {
		return fromJson(json, type, new GsonBuilder().setDateFormat(dd_MM_yyyy.toPattern()));
	}

	public static <T> T fromJson(String json, Class<T> clazz, String dateFormat) {
		return fromJson(json, clazz, new GsonBuilder().setDateFormat(dateFormat));
	}

	public static <T> T fromJson(String json, Class<T> clazz, GsonBuilder gsonBuilder) {
		return gsonBuilder.create().fromJson(json, clazz);
	}

	public static <T> T fromJson(String json, Type type, GsonBuilder gsonBuilder) {
		return gsonBuilder.create().fromJson(json, type);
	}

	public static <T> List<T> fromJson(String json, TypeToken<List<T>> tt) {
		return fromJson(json, tt.getType());
	}

	public static void serialize(Object object, HttpServletResponse response, GsonBuilder gb){
		try {
			response.setContentType("text/plain");
			response.getOutputStream().write(toJson(object, gb).getBytes("UTF-8"));
		} catch (Exception e) {
//			logger.error("Exception: ", e);
		}
	}

	public static void serialize(HttpServletResponse response){
		HashMap<String, Object> map = new HashMap<>();
		serialize(map, response, dd_MM_yyyy.toPattern());
	}

	public static void serialize(Object object, HttpServletResponse response){
		serialize(object, response, dd_MM_yyyy.toPattern());
	}

	public static void serialize(Object object, HttpServletResponse response, String dateFormat){
		try {
			response.setContentType("text/plain");
			response.getOutputStream().write(toJson(object, dateFormat).getBytes("UTF-8"));
		} catch (Exception e) {
//			logger.error("Exception: ", e);
		}
	}

	public static String readableFileSize(long size) {
	    if(size <= 0) return "0 KB";
	    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
