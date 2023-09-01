//package com.example.productservice;
//
//import com.example.productservice.dto.ProductRequestDtoIn;
//import com.example.productservice.repository.ProductRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import org.junit.jupiter.api.Assertions;
//
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
//class ProductServiceApplicationTests {
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private ProductRepository productRepository;
//
////    static {
////        mongoDBContainer.start();
////    }
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
//        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
//
//
//    //these are Integration tests
//
//    @Test
//    void shouldCreateProduct() throws Exception {
//        ProductRequestDtoIn productRequestDtoIn = getProductRequestDtoIn();
//        String productRequestDtoInAsStr = objectMapper.writeValueAsString(productRequestDtoIn);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(productRequestDtoInAsStr))
//                .andExpect(status().isCreated());
//        Assertions.assertEquals(1, productRepository.findAll().size());
//
//    }
//
//    private ProductRequestDtoIn getProductRequestDtoIn() {
//        return ProductRequestDtoIn.builder()
//                .name("iphone 13")
//                .description("iphone 13 descr")
//                .price(BigDecimal.valueOf(1200))
//                .build();
//    }
//
//}
