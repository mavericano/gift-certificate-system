package com.epam.esm.core.unit;

import com.epam.esm.core.converter.EntityDtoConverter;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.impl.GiftCertificateServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private EntityDtoConverter entityDtoConverter;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    public void shouldReturnGiftCertificateIfExistsById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        long id = 1;
        giftCertificate.setId(id);
        giftCertificateDto.setId(id);

        when(entityDtoConverter.
                toDto(giftCertificate, new HashSet<>())).
                thenReturn(giftCertificateDto);
        when(giftCertificateRepository.getGiftCertificateById(id)).thenReturn(Optional.of(giftCertificate));

        Assertions.assertNotNull(giftCertificateService.getGiftCertificateById(String.valueOf(id)));

        verify(entityDtoConverter).toDto(giftCertificate, new HashSet<>());
        verify(giftCertificateRepository).getGiftCertificateById(id);
    }

    @Test
    public void shouldThrowInvalidIdExceptionIfIdIsNotNumeric() {
        Assertions.assertThrows(InvalidIdException.class, () -> giftCertificateService.getGiftCertificateById("a"));
    }

    @Test
    public void shouldThrowNoSuchRecordExceptionIfGiftCertificateNotExistsById() {
        long id = 1;
        when(giftCertificateRepository.getGiftCertificateById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> giftCertificateService.getGiftCertificateById(String.valueOf(id)));

        verify(giftCertificateRepository).getGiftCertificateById(id);
    }

    @Test
    public void shouldReturnEmptyListIfRepositoryIsEmpty(){
        when(giftCertificateRepository.getAllGiftCertificates()).thenReturn(new ArrayList<>());

        Assertions.assertTrue(giftCertificateService.getAllGiftCertificates().isEmpty());

        verify(giftCertificateRepository).getAllGiftCertificates();
    }

    @Test
    public void shouldReturnNonEmptyListIfRepositoryIsNonEmpty() {
        when(giftCertificateRepository.getAllGiftCertificates()).thenReturn(Arrays.asList(new GiftCertificate(), new GiftCertificate()));

        Assertions.assertFalse(giftCertificateService.getAllGiftCertificates().isEmpty());

        verify(giftCertificateRepository).getAllGiftCertificates();
    }

    @Test
    public void shouldThrowInvalidRecordExceptionIfSortTypeNotSpecified(){
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setSortType("name");
        Assertions.assertThrows(InvalidRecordException.class, () -> giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto));

        verifyNoInteractions(giftCertificateRepository);
    }

    @Test
    public void shouldThrowInvalidRecordExceptionIfSortByNotSpecified(){
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setSortBy("DESC");
        Assertions.assertThrows(InvalidRecordException.class, () -> giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto));

        verifyNoInteractions(giftCertificateRepository);
    }

    @Test
    public void shouldReturnGiftCertificateIfExistsByPartOfName(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Unlimited candy supply");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Unlimited candy supply");
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setName("candy suppl");
        when(giftCertificateRepository.getAllGiftCertificatesByRequirements(searchParamsDto)).thenReturn(Collections.singletonList(giftCertificate));
        when(entityDtoConverter.
                toDto(giftCertificate, new HashSet<>())).
                thenReturn(giftCertificateDto);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements(searchParamsDto);
    }

    @Test
    public void shouldReturnGiftCertificateIfExistsByPartOfDescription(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("This certificate provides unlimited candy supply to the owner and owner only!");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setDescription("This certificate provides unlimited candy supply to the owner and owner only!");
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setDescription("candy suppl");

        when(giftCertificateRepository.getAllGiftCertificatesByRequirements(searchParamsDto)).thenReturn(Collections.singletonList(giftCertificate));
        when(entityDtoConverter.
                toDto(giftCertificate, new HashSet<>())).
                thenReturn(giftCertificateDto);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements(searchParamsDto);
    }

    @Test
    public void shouldReturnGiftCertificateIfExistsByTagName() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setTagName("candy");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(1, "candy"));
        giftCertificateDto.setTagSet(tagSet);

        when(giftCertificateRepository.getAllGiftCertificatesByRequirements(searchParamsDto)).thenReturn(Collections.singletonList(giftCertificate));
        when(giftCertificateRepository.getAllTagsForGiftCertificateById(1)).thenReturn(tagSet);
        when(entityDtoConverter.
                toDto(giftCertificate, tagSet)).
                thenReturn(giftCertificateDto);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements(searchParamsDto);
    }

    @Test
    public void shouldAddGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        long id = 1;
        giftCertificate.setId(id);
        giftCertificateDto.setId(id);
        Set<Tag> tagSet = new HashSet<>();
        giftCertificateDto.setTagSet(tagSet);

        when(giftCertificateRepository.addGiftCertificate(giftCertificate)).thenReturn(giftCertificate);
        when(tagRepository.fetchAndAddNewTags(tagSet)).thenReturn(tagSet);
        when(entityDtoConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        when(entityDtoConverter.
                toDto(giftCertificate, tagSet)).
                thenReturn(giftCertificateDto);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.addGiftCertificate(giftCertificateDto));

        verify(giftCertificateRepository).addGiftCertificate(giftCertificate);
    }
}