package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@ApplicationScoped
public class ClienteRest
{
	//ESSE ESTÁ FUNCIONANDO!!!!!
	@SuppressWarnings("unchecked")
	public boolean consultaPab()
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			String itemListaPaa;
			List<String> lista = new ArrayList<String>();
			
			URL url = new URL("https://swapi.co/api/people/2");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Falha: Erro no HTTP code "
						+ conn.getResponseCode());
			}
			
			String response = new String();
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String output;
			while ((output = br.readLine()) != null) {
				response += output;
				System.out.println("response "+response);
			}
			
			try {
				map = mapper.readValue(response, Map.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (Map.Entry<String, String> info : map.entrySet())
			{
				while (info.getKey().equalsIgnoreCase("films"))
				{
					lista.add(info.getValue());
				}
			}
			
//			for (String str : lista)
//			{
//				System.out.println(">>> "+str.toString());
//			}
			
//			for (Map.Entry<String, String> entrada : map.entrySet())
//			{
//				if (entrada.getKey().equalsIgnoreCase("films"))
//				{
//					itemListaPaa = entrada.getValue();
//					if (itemListaPaa.equalsIgnoreCase("blue")||itemListaPaa.equalsIgnoreCase("green")) 
//					{
//						System.out.println("GALÃ");
//					}
//					else 
//					{
//						System.out.println("OLHO DE POBRE");
//					}
//					System.out.println(">>>FILM "+itemListaPaa);
//				}
//			}

			conn.disconnect();
		  }
		
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return false;
}

}
