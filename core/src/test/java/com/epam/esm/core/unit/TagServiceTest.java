package com.epam.esm.core.unit;

import com.epam.esm.core.converter.TagMapper;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void shouldReturnTagIfExistsById() {
        long id = 1;
        String name = "candy";
        TagDto tagDto = TagDto.builder().id(id).name(name).build();
        Tag tag = Tag.builder().id(id).name(name).build();

        when(tagRepository.getTagById(id)).thenReturn(Optional.of(tag));

        Assertions.assertEquals(tagDto, tagService.getTagById(String.valueOf(id)));

        verify(tagRepository).getTagById(id);
    }

    @Test
    public void shouldThrowInvalidIdExceptionIfIdIsNotNumeric() {
        Assertions.assertThrows(InvalidIdException.class, () -> tagService.getTagById("a"));
    }

    @Test
    public void shouldThrowNoSuchRecordExceptionIfTagNotExistsById() {
        long id = 1;
        when(tagRepository.getTagById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> tagService.getTagById(String.valueOf(id)));
    }

    @Test
    public void shouldReturnEmptyListIfRepositoryIsEmpty(){
        when(tagRepository.getAllTags(1, 1, "id", "asc")).thenReturn(new ArrayList<>());

        Assertions.assertTrue(tagService.getAllTags(1, 1, "id", "asc").isEmpty());

        verify(tagRepository).getAllTags(1, 1, "id", "asc");
    }

    @Test
    public void shouldAddTag() {
        long id = 1;
        String name = "candy";
        TagDto tagDto = TagDto.builder().id(id).name(name).build();
        Tag tag = Tag.builder().id(id).name(name).build();


        when(tagRepository.addTag(tag)).thenReturn(tag);

        Assertions.assertEquals(tagDto, tagService.addTag(tagDto));

        verify(tagRepository).addTag(tag);
    }

    @Test
    public void shouldReturnTopTag() {
        Tag candy = Tag.builder().id(1).name("candy").build();
        TagDto candyDto = TagMapper.INSTANCE.tagToTagDto(candy);
        Tag puppy = Tag.builder().id(2).name("puppy").build();
        Tag kitty = Tag.builder().id(3).name("kitty").build();
        GiftCertificate candyPuppy = GiftCertificate.builder().id(1).tagSet(new HashSet<>(Arrays.asList(candy, puppy))).build();
        GiftCertificate candyKitty = GiftCertificate.builder().id(2).tagSet(new HashSet<>(Arrays.asList(candy, kitty))).build();
        GiftCertificate candyKittyPuppy = GiftCertificate.builder().id(3).tagSet(new HashSet<>(Arrays.asList(candy, kitty, puppy))).build();
        User user = User.builder().id(1).username("dr_grave").build();
        Order firstOrder = Order.builder().orderId(1).customer(user).certificates(Collections.singletonList(candyPuppy)).build();
        Order secondOrder = Order.builder().orderId(2).customer(user).certificates(Collections.singletonList(candyKitty)).build();
        Order thirdOrder = Order.builder().orderId(3).customer(user).certificates(Collections.singletonList(candyKittyPuppy)).build();
        user.setOrders(Arrays.asList(firstOrder, secondOrder, thirdOrder));
        when(userRepository.getMaxOrderSumUser()).thenReturn(user);

        Assertions.assertEquals(candyDto, tagService.getTopTag());

        verify(userRepository).getMaxOrderSumUser();
    }
}
