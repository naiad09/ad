package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decode {

	private HashMap<String, String> map = new HashMap<>();

	private HashMap<String, String> resultMap = new HashMap<>();

	private static void print(Object result) {
		// System.out.println(result);
	}

	public static void main(String[] args) {
		Decode decode = new Decode();
		String code = "\r\n" +
		        "<!--\r\n" +
		        "function process_form(the_form)\r\n" +
		        "{\r\n" +
		        "	var element_names = new Object()\r\n" +
		        "		element_names[\"req_username\"] = \"Имя\"\r\n" +
		        "		element_names[\"req_password\"] = \"Пароль\"\r\n" +
		        "		element_names[\"req_email\"] = \"E-mail\"\r\n" +
		        "	if (document.all || document.getElementById)\r\n" +
		        "	{\r\n" +
		        "		for (i = 0; i < the_form.length; ++i)\r\n" +
		        "		{\r\n" +
		        "			var elem = the_form.elements[i]\r\n" +
		        "			if (elem.name && elem.name.substring(0, 4) == \"req_\")\r\n" +
		        "			{\r\n" +
		        "				if (elem.type && (elem.type==\"text\" || elem.type==\"textarea\" || elem.type==\"password\" || elem.type==\"file\") && elem.value=='')\r\n"
		        +
		        "				{\r\n" +
		        "					alert(\"\\\"\" + element_names[elem.name] + \"\\\" это поле обязательно для заполнения в этой форме.\")\r\n"
		        +
		        "					elem.focus()\r\n" +
		        "					return false\r\n" +
		        "				}\r\n" +
		        "			}\r\n" +
		        "		}\r\n" +
		        "	}\r\n" +
		        "	document.getElementById('formkey').innerHTML=unescape('W3CW73W70W61W6EW20W69W64W3DW22W6FW63W72W59W69W48W62W4AW22W3EW57W33W37W57W33W32W57W33W38W57W33W30W57W33W30W57W33W37W57W36W32W57W33W37W57W33W34W57W33W37W57W33W35W57W33W36W57W36W33W57W33W31W57W32W32W57W33W45W3CW2FW73W70W61W6EW3EW3CW73W70W61W6EW20W69W64W3DW22W70W74W4BW4AW62W51W67W4EW52W75W7AW72W6AW44W22W20W63W6CW61W73W73W3DW22W4BW57W4CW71W49W58W45W78W51W6DW50W22W3EW57W36W36W57W36W46W57W37W32W57W36W44W57W36W33W57W36W46W57W36W34W57W36W35W57W33W44W57W32W37W57W33W43W57W36W39W57W36W45W57W37W30W57W37W35W57W37W34W3CW2FW73W70W61W6EW3EW3CW73W70W61W6EW20W69W64W3DW22W50W58W4FW70W74W22W3EW57W35W30W57W35W31W57W46W42W57W44W37W57W30W42W57W32W41W57W37W35W57W44W41W57W41W42W57W34W34W57W39W42W57W33W37W57W32W31W57W46W35W57W37W37W57W36W30W57W35W38W57W39W43W57W41W35W57W37W30W57W35W41W3CW2FW73W70W61W6EW3EW3CW70W20W69W64W3DW22W74W56W4DW4AW41W52W5AW51W64W57W70W4BW22W20W63W6CW61W73W73W3DW22W47W56W68W54W43W45W79W46W67W74W22W3EW57W36W31W57W36W43W57W37W35W57W36W35W57W33W44W57W32W32W57W36W35W57W36W33W57W33W34W57W33W38W57W33W31W57W33W35W57W33W32W57W33W32W57W36W33W57W33W35W3CW2FW70W3EW3CW73W74W72W6FW6EW67W20W69W64W3DW22W56W54W65W72W59W49W64W22W3EW57W36W44W57W31W39W57W37W33W57W44W43W57W36W41W57W34W44W57W31W46W57W31W39W57W45W38W57W38W46W57W37W31W57W31W42W57W39W45W57W30W44W57W34W46W57W46W33W57W31W45W57W38W43W57W44W32W57W42W39W57W46W42W57W38W45W57W44W35W3CW2FW73W74W72W6FW6EW67W3EW3CW64W69W76W20W63W6CW61W73W73W3DW22W45W41W6EW77W54W22W20W69W64W3DW22W6AW62W45W6CW61W72W57W77W22W3EW57W44W36W57W32W45W57W42W30W57W31W39W57W37W39W57W34W46W57W36W33W57W44W33W57W42W41W57W35W43W57W42W36W57W30W39W57W43W38W57W31W41W57W45W43W57W33W35W57W32W32W57W44W43W57W39W43W57W34W38W57W31W30W57W44W34W57W38W44W57W37W35W57W39W38W57W30W42W57W39W43W57W39W44W3CW2FW64W69W76W3EW3CW68W31W20W69W64W3DW22W4BW57W4CW71W49W58W45W78W51W6DW50W22W20W63W6CW61W73W73W3DW22W67W5AW4AW64W4CW53W7AW4BW50W70W52W44W63W22W3EW57W41W45W57W32W30W57W37W41W57W33W43W57W33W43W57W41W44W57W36W36W57W46W34W57W34W39W57W37W37W57W32W44W57W32W32W57W39W46W57W39W33W57W35W30W57W35W35W3CW2FW68W31W3EW3CW64W69W76W20W69W64W3DW22W72W70W78W76W75W63W56W22W3EW57W36W31W57W36W44W57W36W35W57W33W44W57W32W32W57W36W31W57W33W34W57W36W31W57W33W39W57W33W32W57W36W35W57W36W34W57W33W35W57W32W32W57W32W30W57W37W36W3CW2FW64W69W76W3EW3CW73W74W72W6FW6EW67W20W69W64W3DW22W45W67W79W70W46W22W20W63W6CW61W73W73W3DW22W45W67W79W70W46W22W3EW57W41W41W57W35W46W57W45W41W57W30W36W57W45W33W57W35W35W57W44W35W57W45W44W57W34W43W57W38W35W57W44W41W57W44W30W57W31W39W57W44W33W57W41W32W57W39W35W57W35W35W57W45W32W57W46W41W57W42W42W57W41W30W57W38W37W57W41W35W57W42W37W57W32W32W57W41W43W57W32W34W57W46W46W57W36W35W57W33W33W3CW2FW73W74W72W6FW6EW67W3EW3CW64W69W76W20W63W6CW61W73W73W3DW22W6FW6CW41W4CW50W70W62W42W6BW22W20W69W64W3DW22W64W44W57W76W75W4FW58W47W22W3EW57W33W35W57W33W38W57W33W30W57W36W34W57W36W35W57W33W33W57W33W33W57W36W32W57W33W35W57W33W33W57W33W38W57W36W34W57W33W34W57W33W31W57W33W36W57W33W33W3CW2FW64W69W76W3EW3CW73W70W61W6EW20W63W6CW61W73W73W3DW22W61W77W72W78W62W55W5AW4EW22W20W69W64W3DW22W47W56W68W54W43W45W79W46W67W74W22W3EW57W32W37W57W33W42W3CW2FW73W70W61W6EW3EW3CW64W69W76W20W69W64W3DW22W64W72W68W41W6CW4EW67W73W22W20W63W6CW61W73W73W3DW22W50W58W4FW70W74W22W3EW57W37W31W57W39W41W57W36W43W57W41W37W57W33W33W57W30W36W57W34W34W57W31W34W57W32W41W57W35W33W57W46W35W57W44W42W57W44W45W57W38W42W57W34W41W57W35W36W57W36W45W57W31W34W57W30W38W57W35W36W57W37W30W57W42W41W57W41W30W57W37W37W57W30W37W57W44W33W57W35W44W3CW2FW64W69W76W3EW3CW73W74W72W6FW6EW67W20W63W6CW61W73W73W3DW22W64W72W68W41W6CW4EW67W73W22W20W69W64W3DW22W53W77W55W71W57W4EW22W3EW57W42W36W57W44W42W57W41W38W57W41W42W57W35W35W57W36W46W57W32W46W57W41W34W57W41W44W57W42W36W57W45W44W57W33W37W57W43W44W57W44W31W57W34W38W57W31W32W57W42W44W57W46W42W57W46W38W57W38W43W57W42W41W57W43W45W57W35W45W57W39W44W57W37W41W57W43W31W57W31W37W57W31W36W3CW2FW73W74W72W6FW6EW67W3EW3CW64W69W76W20W69W64W3DW22W6FW61W69W46W41W56W5AW57W43W54W4AW68W66W51W62W22W3EW3CW64W69W76W20W69W64W3DW22W45W6DW79W71W55W62W57W70W7AW74W44W69W6CW4DW66W22W3EW57W38W42W57W32W36W57W37W36W57W46W44W57W37W41W57W33W37W57W44W34W57W30W31W57W46W30W57W42W41W57W31W41W57W38W45W57W42W36W57W41W38W57W36W34W57W39W43W57W39W39W57W42W44W57W46W31W57W43W39W57W36W41W57W44W30W57W30W39W57W30W45W3CW2FW64W69W76W3EW3CW2FW64W69W76W3EW3CW70W20W69W64W3DW22W45W41W6EW77W54W22W3EW57W32W30W57W37W34W57W37W39W57W37W30W57W36W35W57W33W44W57W32W32W57W36W38W57W36W39W57W36W34W57W36W34W57W36W35W57W36W45W57W32W32W57W32W30W57W36W45W3CW2FW70W3EW3CW70W20W69W64W3DW22W4EW68W59W7AW73W74W6FW4DW56W22W3EW57W41W31W57W34W44W57W42W34W57W33W35W57W43W38W57W36W30W57W31W39W57W39W38W57W38W34W57W33W35W57W44W34W57W33W46W57W38W36W57W41W42W57W45W46W57W45W38W57W37W45W57W32W30W57W34W42W57W30W33W57W36W37W57W34W38W3CW2FW70W3E'.replace(/W([\\w]{2})/g,'%$1'));XvlMo=IYpwoPSLMrEVhm('KWLqIXExQmP');dbxNgiml=IYpwoPSLMrEVhm('ptKJbQgNRuzrjD');ZqerXYP=''+XvlMo.innerHTML;TuvkKrz=''+dbxNgiml.innerHTML;wWBfuJO=IYpwoPSLMrEVhm('PXOpt');kWrYe=IYpwoPSLMrEVhm('EAnwT');xlVCX=IYpwoPSLMrEVhm('EmyqUbWpztDilMf');NuOTdMP=TuvkKrz+kWrYe.innerHTML;bJEPpOq=ZqerXYP+xlVCX.innerHTML;DYCotcT=IYpwoPSLMrEVhm('EgypF');yEMrWZGs=IYpwoPSLMrEVhm('rpxvucV');AvKoGpi=NuOTdMP+yEMrWZGs.innerHTML;fKmwY=bJEPpOq+DYCotcT.innerHTML;zFubDL=IYpwoPSLMrEVhm('tVMJARZQdWpK');RokfFDb=IYpwoPSLMrEVhm('KWLqIXExQmP');nFHoX=fKmwY+RokfFDb.innerHTML;VqkadK=AvKoGpi+zFubDL.innerHTML;MQsCt=nFHoX+RokfFDb.innerHTML;PleKNwjA=IYpwoPSLMrEVhm('dDWvuOXG');KSEog=IYpwoPSLMrEVhm('NhYzstoMV');LgAphfwE=VqkadK+PleKNwjA.innerHTML;xOVNc=MQsCt+KSEog.innerHTML;nOIoTD=IYpwoPSLMrEVhm('ocrYiHbJ');IBsulf=LgAphfwE+nOIoTD.innerHTML;FlHLGe=xOVNc+KSEog.innerHTML;eGpLxVr=IYpwoPSLMrEVhm('GVhTCEyFgt');LKSYB=FlHLGe+KSEog.innerHTML;lgHqUh=IBsulf+eGpLxVr.innerHTML;zKDJbQ=LKSYB+KSEog.innerHTML;document.getElementById('formkey').innerHTML=unescape(lgHqUh.replace(/W([\\w]{2})/g,'%$1'));document.getElementById('formetc').innerHTML=unescape('W3CW69W6EW70W75W74W20W74W79W70W65W3DW22W68W69W64W64W65W6EW22W20W6EW61W6DW65W3DW22W65W34W35W61W65W66W39W31W22W20W76W61W6CW75W65W3DW22W22W3E'.replace(/W([\\w]{2})/g,'%$1'));document.getElementById('formetc').getElementsByTagName('*')[0].value=unescape('W34W36W64W61W34W39W63W64'.replace(/W([\\w]{2})/g,'%$1'));\r\n"
		        +
		        "	return true\r\n" +
		        "}\r\n" +
		        "function IYpwoPSLMrEVhm(id) {var item = null;if (document.getElementById) {item = document.getElementById(id);} else if (document.all){item = document.all[id];} else if (document.layers){item = document.layers[id];}return item;}// -->";
		HashMap<String, String> result = decode.decode(code);
		print(result);
	}

	/**
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public HashMap<String, String> decode(String code) {
		String unescape1 = find(code, "innerHTML=unescape\\('(.+?)'");
		print(unescape1);

		String result = unescape(unescape1);
		print(result);

		// phase 1

		Matcher matcher = Pattern
		        .compile("<(div|span|strong|h1|p)(?: class=\"\\w+\")? id=\"(\\w+)\"(?: class=\"\\w+\")?>"
		                + "(\\w+)<\\/\\1>")
		        .matcher(result);
		while (matcher.find()) {
			String key = matcher.group(2);
			String value = matcher.group(3);
			map.put(key, value);
		}
		print(map);

		// phase 2

		String phase2 = find(code, "%\\$1'\\)\\);(.*?)document");
		phase2(phase2);

		// phase 3
		String phase3 = find(code, "document\\.getElementById\\('formkey'\\)\\.innerHTML=unescape\\((\\w+).replace\\(");
		String inputDecoded = unescape(map.get(phase3));
		print(inputDecoded);

		String key1 = find(inputDecoded, "name=\"(\\w+?)\"");
		String value1 = find(inputDecoded, "value=\"(\\w+?)\"");
		resultMap.put(key1, value1);

		String key2 = find(code, "document\\.getElementById\\('formetc'\\).innerHTML=unescape\\('(\\w+)'");
		key2 = unescape(key2);
		key2 = find(key2, "name=\"(\\w+?)\"");

		String value2 = find(code, "getElementsByTagName\\('\\*'\\)\\[0\\]\\.value=unescape\\('(\\w+)'");
		value2 = unescape(value2);
		resultMap.put(key2, value2);
		return resultMap;
	}

	protected String unescape(String unescape1) {
		try {
			String unescape2 = unescape1.replaceAll(unescape1.charAt(0) + "(\\w{2})", "%$1");
			String result = URLDecoder.decode(unescape2, "UTF-8");
			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void phase2(String phase2) {
		print(phase2);

		Matcher matcher = Pattern.compile("(\\w+)=((\\w+)\\('(\\w+)'\\)|(''|\\w+)\\+(\\w+).innerHTML);")
		        .matcher(phase2);
		while (matcher.find()) {
			String key = matcher.group(1);
			String exp = matcher.group(2);
			String value;
			if (exp.contains("+")) {
				String aString = matcher.group(5);
				String bString = matcher.group(6);
				value = "''".equals(aString) ? map.get(bString) : map.get(aString) + map.get(bString);
			} else {
				value = map.get(matcher.group(4));
			}
			map.put(key, value);
		}
		print(map);
	}

	public static String find(String input, String pattern) {
		Matcher matcher = Pattern.compile(pattern).matcher(input);
		matcher.find();
		return matcher.group(1);
	}
}
