package com.epam.esm.core.unit;

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
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("candy");

        when(tagRepository.getTagById(1)).thenReturn(Optional.of(tag));

        Assertions.assertEquals(tag, tagService.getTagById(String.valueOf(1)));

        verify(tagRepository).getTagById(1);
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
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("candy");

        when(tagRepository.addTag(tag)).thenReturn(tag);
//FIXME
        //Assertions.assertEquals(tag, tagService.addTag(tag));

        verify(tagRepository).addTag(tag);
    }
}
