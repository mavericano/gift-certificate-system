package com.epam.esm.core.unit;

import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

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
        when(tagRepository.getAllTags()).thenReturn(new ArrayList<>());

        Assertions.assertTrue(tagService.getAllTags().isEmpty());

        verify(tagRepository).getAllTags();
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
}
