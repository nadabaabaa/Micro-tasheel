    package tn.esprit.infini.Pidev.security;


    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import tn.esprit.infini.Pidev.Services.user_management.UserDetailsServiceImpl;

    import java.util.Arrays;

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(
            // securedEnabled = true,
            // jsr250Enabled = true,
            prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        UserDetailsServiceImpl userDetailsService;

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
            return new AuthTokenFilter();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests().antMatchers("/api/auth/**","/Credit/CalculMensualite0fixe",
                            "/Credit/CalculMensualitevariable","/Credit/listemontantrestant","/Credit/amountafterinsurance",
                            "/Credit/listeamortissement","/Credit/listetauxinterests","/Credit/getmm","/Pack/getPack","/Invest/mensualite",
                            "/Invest/taxes").permitAll()
                            .antMatchers("/api/test/**").permitAll()
                            .antMatchers("/Invest/deleteInvest/**",
                                    "/Invest/updateInvest","/Invest/getinvestsbyiduser/**",
                                    "/Credit/getcreditsbyiduser/**",
                                    "/Credit/deleteCredits/**","Cart/getCart",
                                        "/Cart/getCartById/{idCart}",
                                    "/Cart/getSumCartInterest/**",
                                    "/Cart/getSumCartInterest/**"
                                    ).hasAnyRole("ADMIN", "USER")
                            .antMatchers("/Invest/investbystatut",
                                    "/Invest/getInvestById/**",
                                    "/Invest/deleteInvest/**",
                                    "/Invest/investbystatut",
                                    "/Invest/amount",
                                    "/Invest/searchs",
                                    "/Invest/getInvest",
                                    "/Credit/Credits",
                                    "/Complaint/displaywithIdComplaint/**",
                                    "/Complaint/statistics",
                                    "/Complaint/displayComplaint/**",
                                    "/Complaint/getcomplaintbyType",
                                    "/Complaint/afficherarchive",
                                    "/Complaint/getAllcomplaints",
                                    "/Complaint/sendResolvedEmails",
                                    "/Credit/search/**",
                                    "/Credit/averageInterestRate"   ,
                                    "/Credit/percentageByStatus",
                                    "/Credit/repayment-rates",
                                    "/Credit/totalAmountOfLoans",
                                    "/Credit/totalNumberOfLoans",
                                    "/Insurance/displaycurrentinsurance",
                                    "/Insurance/displayArchivedinsurance",
                                    "/Insurance/displaywithId/**",
                                    "/Insurance/getinsurancebyType","/Cart/addCart",
                                    "/Cart/deleteCart/**",
                                    "/Cart/getSumCart/**",
                                    "/Cart/most-expensive/**",
                                    "/Pack/mostLikedPacks",
                                    "/Pack/mostDislikedPacks",
                                    "/Pack/addPack",
                                    "/Pack/updatePack",
                                    "/Pack/deletePack/**",
                                    "/Pack/average-rating/**",
                                    "/Pack/average-rating/**"
                            ).hasRole("ADMIN")
                            .antMatchers("/Credit/pdf/generates/**",
                                    "/Credit/validatecredit/**",
                                    "/Credit/calculateFicoScore",
                                    "/Credit/InterestRateCalculator",
                                    "/Invest/addInvest",
                                    "/Credit/validatecredit/**",
                                    "/Credit/ajoutCredits",
                                    "/Complaint/addComplaint/**",
                                    "/Complaint/displayComplaint12/**",
                                    "/Insurance/packs/**/insurances/**",
                                    "/Insurance/calculateCostWithDiscount",
                                    "/Insurance/calculate-cost",
                                    "/Insurance/insurance/add/**"
                                    ,"/Cart/updateCart/{IdCart}",
                                    "/Cart/removePackFromCart/{idCart}/**",
                                    "/Cart/**/monthly-price",
                                    "/Cart/recommended-packs/**",
                                    "/Cart/recommended-packs-by-type/**",
                                    "/Cart/clearExpiredPacks",
                                    "/Pack/assignPackToCart/**/**",
                                    "/Pack/addReaction/**/**/**,",
                                     "/Insurance/validate-insurance").hasRole("USER")
                    .anyRequest().authenticated();

            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        }




    }