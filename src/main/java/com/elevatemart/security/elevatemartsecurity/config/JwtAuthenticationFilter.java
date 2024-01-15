package com.elevatemart.security.elevatemartsecurity.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter {}
/*
extends OncePerRequestFilter {


    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTToken jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching Username from Token", e);
                throw new ElevateMartUserAuthenticationFailException("An error occurred while fetching Username from Token "+ e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
                throw new ElevateMartUserAuthenticationFailException("The token has expired. Please Login again "+ e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
                throw new ElevateMartUserAuthenticationFailException("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
            // throw new UserAutheticationFailException("Couldn't find bearer string, header will be ignored");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            try {
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch(Exception exc) {
                logger.error("Unknown Server error occured While validating the Authentication token");
                throw new UnknownServerError("Unknown Server error occured While validating the Authentication token");
            }
        }

        filterChain.doFilter(req, res);
    }
}
 */
