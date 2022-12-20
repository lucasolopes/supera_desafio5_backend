package br.com.banco.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void retornarErro404AoPassarUmNumeroDeContaInvalido() throws Exception {
		URI uri = new URI("/conta?IdConta=100");
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	public void retornarErro400AoPassarNenhumNumeroDeConta() throws Exception {
		URI uri = new URI("/conta");
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(400));
		
	}
	@Test
	public void retornar200AoPassarNumeroDaContaValido() throws Exception {
		URI uri = new URI("/conta?IdConta=1");
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	
}
