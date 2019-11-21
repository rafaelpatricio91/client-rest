package cliente;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@ApplicationScoped
public class Consuming
{
	String msge = "NADA";
	
	Map<String, String> map = new HashMap<String, String>();
	
	@SuppressWarnings("unchecked")
	public String consulta() throws JsonParseException, JsonMappingException, IOException
	{
		String msge = "NADA";
		//Mapeador do Jackson
		ObjectMapper mapper = new ObjectMapper();
		//Criação do cliente
		Client client = ClientBuilder.newClient();
		//Alvo de acesso, uri da api
		WebTarget target = client.target("https://swapi.co/api/people/1/");
		//Jogando json de retorno para string
		String msg = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		//Jogando o json no map com o Jackson
		map = mapper.readValue(msg, Map.class);
		
		//Navegando pelo map e pegando o valor pela chave gender
		for (Map.Entry<String, String> entrada : map.entrySet())
		{
			if (entrada.getKey().equalsIgnoreCase("gender"))
			{
				msge = entrada.getValue();
				System.out.println(">>> "+msge);
			}
		}
		
		return msge;
	}

	public String getMsge()
	{
		return msge;
	}

	public void setMsge(String msge)
	{
		this.msge = msge;
	}

	public Map<String, String> getMap()
	{
		return map;
	}

	public void setMap(Map<String, String> map)
	{
		this.map = map;
	}
	
	
	
		
}
