package com.rks.service;

import com.rks.dto.CartDetailsDto;
import com.rks.dto.CartDto;
import com.rks.exceptions.ProductNotFoundException;
import com.rks.model.Cart;
import com.rks.model.CartDetails;
import com.rks.model.Product;
import com.rks.repository.CartDetailsRepository;
import com.rks.repository.CartRepository;
import com.rks.repository.OrderRepository;
import com.rks.repository.ProductRepository;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailsRepository cartDetailsRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void createCartTest() throws NotFoundException, Exception{

        CartDetailsDto cartDetailsDto1 = new CartDetailsDto(1234,12);
        CartDetailsDto cartDetailsDto2 = new CartDetailsDto(1223,12);
        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");
        Product product = new Product(12, "TestProduct", "TestDescription", 56, 64);

        when(productRepository.findOne(anyInt())).thenReturn(product);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto expected = new CartDto(12, Arrays.asList(cartDetailsDto1, cartDetailsDto2), 24 );
        CartDto actual = cartService.createCart(expected);

        assertEquals(actual.getCartId(), expected.getCartId());
        assertEquals(actual.getCartDetailsDtoList(), expected.getCartDetailsDtoList());
    }

    @Test(expected = ProductNotFoundException.class)
    public void createCartTest_throwsNotFoundException() throws NotFoundException{

        CartDetailsDto cartDetailsDto1 = new CartDetailsDto(1234,12);
        CartDetailsDto cartDetailsDto2 = new CartDetailsDto(1223,12);
        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");

        when(productRepository.findOne(anyInt())).thenThrow(new ProductNotFoundException("Unable to find Product"));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto cartDto = new CartDto(1234, Arrays.asList(cartDetailsDto1, cartDetailsDto2), 24 );
        cartService.createCart(cartDto);
    }

    @Test
    public void findAllTest() {

        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");

        when(cartRepository.findAll()).thenReturn(Arrays.asList(cart));
        List<Cart> carts = cartService.findAll();

        assert(carts.size() == 1);
        assert(carts.contains(cart));
    }

    @Test
    public void findByIdTest() {

        Cart expected = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");

        when(cartRepository.findOne(12)).thenReturn(expected);
        Cart actual = cartService.findById(12);

        assertEquals(expected, actual);
    }

    @Test(expected = ProductNotFoundException.class)
    public void findByIdTest_throwsException() {

        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");

        when(cartRepository.findAll()).thenThrow(new ProductNotFoundException("Unable to find Product with id:" + cart.getCartId()));
        cartService.findAll();
    }

    @Test
    public void updateCartTest() throws ProductNotFoundException{

        CartDetailsDto cartDetailsDto1 = new CartDetailsDto(1234,12);
        CartDetailsDto cartDetailsDto2 = new CartDetailsDto(1223,12);
        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");
        Product product = new Product(12, "TestProduct", "TestDescription", 56, 64);

        when(cartRepository.findOne(anyInt())).thenReturn(cart);
        when(productRepository.findOne(anyInt())).thenReturn(product);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto expected = new CartDto(12, Arrays.asList(cartDetailsDto1, cartDetailsDto2), 24 );
        CartDto actual = cartService.updateCart(expected);

        assertEquals(actual.getCartId(), expected.getCartId());
        assertEquals(actual.getCartDetailsDtoList(), expected.getCartDetailsDtoList());
    }

    @Test(expected = ProductNotFoundException.class)
    public void updateCartTest_throwsProductNotFoundException() throws ProductNotFoundException{

        CartDetailsDto cartDetailsDto1 = new CartDetailsDto(1234,12);
        CartDetailsDto cartDetailsDto2 = new CartDetailsDto(1223,12);
        Cart cart = new Cart(12, new Double(80), new ArrayList<CartDetails>(), "In Progress");
        Product product = new Product(12, "TestProduct", "TestDescription", 56, 64);

        when(cartRepository.findOne(anyInt())).thenReturn(cart);
        when(productRepository.findOne(anyInt())).thenThrow(new ProductNotFoundException("Product Not Found"));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto expected = new CartDto(12, Arrays.asList(cartDetailsDto1, cartDetailsDto2), 24 );
        cartService.updateCart(expected);
    }
}
