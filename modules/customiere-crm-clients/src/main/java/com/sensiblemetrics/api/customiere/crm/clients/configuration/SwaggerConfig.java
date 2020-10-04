package com.sensiblemetrics.api.customiere.crm.clients.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("org.zpf"))
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    public springfox.documentation.swagger.web.SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId(CLIENT_ID)
            .clientSecret(CLIENT_SECRET)
            .scopeSeparator(StringUtils.SPACE)
            .appName("pem-common")
            .scopeSeparator(",")
            .additionalQueryStringParams(null)
            .useBasicAuthenticationWithAccessCodeGrant(true)
            .build();
    }

    @Bean
    public Docket configure(final TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET,
                Lists.newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("500 message")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                    new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden!")
                        .build()))
            .enable(true)
            .apiInfo(apiInfo())
            .alternateTypeRules(
                new RecursiveAlternateTypeRule(typeResolver,
                    Arrays.asList(
                        AlternateTypeRules.newRule(
                            typeResolver.resolve(Mono.class, WildcardType.class),
                            typeResolver.resolve(WildcardType.class)),
                        AlternateTypeRules.newRule(
                            typeResolver.resolve(ResponseEntity.class, WildcardType.class),
                            typeResolver.resolve(WildcardType.class))
                    )))
            .alternateTypeRules(new RecursiveAlternateTypeRule(typeResolver,
                Arrays.asList(
                    AlternateTypeRules.newRule(
                        typeResolver.resolve(Flux.class, WildcardType.class),
                        typeResolver.resolve(List.class, WildcardType.class)),
                    AlternateTypeRules.newRule(
                        typeResolver.resolve(ResponseEntity.class, WildcardType.class),
                        typeResolver.resolve(WildcardType.class))
                )))
            .enableUrlTemplating(true)
            .forCodeGeneration(false)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .directModelSubstitute(LocalDateTime.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .host("localhost")
            .protocols(Sets.newHashSet("http", "https"))
            .produces(Sets.newHashSet(MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .tags(
                new Tag(TAG_MAIN_FUNCTIONAL, "All apis relating to main service purpose"),
                new Tag(TAG_RESOURCE_MANAGEMENT, "All apis relating to upload resources")
            );
    }

    private AlternateTypePropertyBuilder property(final Class<?> type, final String name) {
        return new AlternateTypePropertyBuilder()
            .withName(name)
            .withType(type)
            .withCanRead(true)
            .withCanWrite(true);
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .validatorUrl(null)
            .build();
    }

    //
//    private AuthorizationScope[] scopes() {
//        final AuthorizationScope[] scopes = {
//            new AuthorizationScope("read", "for read operations"),
//            new AuthorizationScope("write", "for write operations"),
//            new AuthorizationScope("audit", "for audit operations")};
//        return scopes;
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//    .securityReferences(defaultAuth())
//            .forPaths(PathSelectors.regex("/api*"))
//            .build();
//    }

//    private List<SecurityReference> defaultAuth() {
//        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        return Collections.singletonList(new SecurityReference("spring_oauth", scopes())); //<18>
//    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Pem ProductItem Fetcher REST API")
            .description("Personalized mailings by products segments")
            .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
            .contact(new Contact("git@pearl.de", "https://git.pearl.de/pem-product-fetcher.git", "support@pearl.de"))
            .license("MIT License")
            .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
            .version("2.0")
            .build();
    }

    /**
     * Recursive {@link AlternateTypeRule} implementation
     */
    public static class RecursiveAlternateTypeRule extends AlternateTypeRule {

        /**
         * Default {@link List} of {@link AlternateTypeRule}
         */
        private final List<AlternateTypeRule> rules = new ArrayList<>();

        public RecursiveAlternateTypeRule(final TypeResolver typeResolver, final List<AlternateTypeRule> rules) {
            super(typeResolver.resolve(Object.class), typeResolver.resolve(Object.class));
            this.rules.addAll(rules);
        }

        @Override
        public ResolvedType alternateFor(final ResolvedType type) {
            final Stream<ResolvedType> rStream = this.rules.stream().flatMap(rule -> Stream.of(rule.alternateFor(type)));
            final ResolvedType newType = rStream.filter(alternateType -> alternateType != type).findFirst().orElse(type);
            if (appliesTo(newType)) {
                return alternateFor(newType);
            }
            return newType;
        }

        @Override
        public boolean appliesTo(final ResolvedType type) {
            return this.rules.stream().anyMatch(rule -> rule.appliesTo(type));
        }
    }
}
