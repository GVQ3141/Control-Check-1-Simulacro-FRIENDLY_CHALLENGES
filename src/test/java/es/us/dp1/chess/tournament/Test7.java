package es.us.dp1.chess.tournament;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Test7 {
    @Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

    private String url = "/api/v1/challenges";
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
		.webAppContextSetup(context)
		.apply(SecurityMockMvcConfigurers.springSecurity())
		.build();
	}



    @Test
    @Transactional
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void test7CanGetRankingAsParticipant() throws JsonProcessingException, Exception{
        mockMvc.perform(get(url+"/1/ranking"))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void test7CannotGetRankingOfNonExistentChallenge() throws JsonProcessingException, Exception{
       mockMvc.perform(get(url+"/474629/ranking"))
			.andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "player2", authorities = {"PLAYER"})
    public void test7CannotGetRankingWithoutFinishedMatches() throws JsonProcessingException, Exception{
       mockMvc.perform(get(url+"/2/ranking"))
			.andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "player4", authorities = {"PLAYER"})
    public void test7CannotGetRankingIfNotParticipant() throws JsonProcessingException, Exception{
       mockMvc.perform(get(url+"/1/ranking"))
			.andExpect(status().isForbidden());
    }

}
