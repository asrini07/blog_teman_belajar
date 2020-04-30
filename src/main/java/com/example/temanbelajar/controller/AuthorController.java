package com.example.temanbelajar.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.AuthorRequestDto;
import com.example.temanbelajar.dto.request.AuthorRequestPassDto;
import com.example.temanbelajar.dto.request.AuthorRequestUpdateDto;
import com.example.temanbelajar.dto.response.AuthorResponseDto;
import com.example.temanbelajar.dto.response.AuthorResponseUpdateDto;
import com.example.temanbelajar.dto.response.OauthResponseDto;
import com.example.temanbelajar.dto.response.PasswordResponseUpdateDto;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthorController
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
	private DataSource dataSource;

	@Autowired
    private ClientDetailsService clientDetailsStore;

    @Autowired
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
	}

    @Autowired
	private AuthenticationManager authenticationManager;

    @GetMapping()
    public ResponseBaseDto<ConfigPage<AuthorResponseDto>> getAllCategories(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){
        
        try {

            Page<AuthorResponseDto> author;

            if (param != null) {
                author = authorService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                author = authorService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<AuthorResponseDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/author",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<AuthorResponseDto> respon = converter.convert(author, url, search);

            return ResponseBaseDto.ok(respon);

        } catch (Exception e) {

            return ResponseBaseDto.error("200", e.getMessage());

        }

    }

    @PostMapping()
    public ResponseBaseDto createAuthor(@RequestBody AuthorRequestDto authorData){

        try {

            Author author = authorService.save(authorData);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    @GetMapping("/{id}")
    public ResponseBaseDto<AuthorResponseDto> detailAuthor(@PathVariable(value = "id") Long authorId) {

        try {

            return ResponseBaseDto.ok(authorService.findById(authorId));

        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseBaseDto updateAuthor(@PathVariable(value = "id") Long authorId, @RequestBody AuthorRequestUpdateDto authorData) {

        try {

            AuthorResponseUpdateDto author = authorService.update(authorId, authorData);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    @PutMapping("{id}/password")
    public ResponseBaseDto updatePassword(@PathVariable(value = "id") Long authorId, @RequestBody AuthorRequestPassDto authorPassDto) {

        try {

            PasswordResponseUpdateDto author = authorService.changePassword(authorId, authorPassDto);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    @DeleteMapping("/")
    public ResponseBaseDto deleteAuthorRequest(@RequestBody Author authorData) {

        try {

            authorService.deleteById(authorData.getId());

            return ResponseBaseDto.ok();

        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    //Normal Login
	@RequestMapping(value="/api/login", method = RequestMethod.POST)
	public  ResponseEntity<OauthResponseDto> login(@RequestParam HashMap<String, String> params) throws Exception
	{
		OauthResponseDto response = new OauthResponseDto();
		Author checkUser =  authorService.findByUsername(params.get("username"));

	    if (checkUser != null)
		{
			try {
				OAuth2AccessToken token = this.getToken(params);
			
				response.setStatus(true);
				response.setCode("200");
				response.setMessage("success");
				response.setData(token);

				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception exception) {
				
                    response.setStatus(false);
                    response.setCode("500");
                    response.setMessage(exception.getMessage());
			}
		} else {
			throw new Exception();
		}
		

		return new ResponseEntity<OauthResponseDto>(response, HttpStatus.UNAUTHORIZED);
    }
    
    private OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException {
		if (params.get("username") == null ) {
			throw new UsernameNotFoundException("username not found");
		}

		if (params.get("password") == null) {
			throw new UsernameNotFoundException("password not found");
		}

		if (params.get("client_id") == null) {
			throw new UsernameNotFoundException("client_id not found");
		}

		if (params.get("client_secret") == null) {
			throw new UsernameNotFoundException("client_secret not found");
		}

		DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);

		AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
		authorizationRequest.setApproved(true);

		OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
		
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				params.get("username"), params.get("password"));
		Authentication authentication = authenticationManager.authenticate(loginToken);

		OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, authentication);
		authenticationRequest.setAuthenticated(true);

		OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);


		return token;
    } 
    
    @Autowired
	public AuthorizationServerTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setAccessTokenValiditySeconds(-1);

		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
    
}