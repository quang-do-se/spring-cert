package com.apress.cems.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages =  {"com.apress.cems.repos"})
public class RepoConfig {
}
