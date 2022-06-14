package com.epam.esm.core.unit;

import com.epam.esm.core.converter.GiftCertificateMapper;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.TagDto;
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

        when(giftCertificateRepository.getGiftCertificateById(id)).thenReturn(Optional.of(giftCertificate));

        Assertions.assertNotNull(giftCertificateService.getGiftCertificateById(String.valueOf(id)));

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
    public void shouldReturnGiftCertificateIfExistsByPartOfName(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Unlimited candy supply");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setTagSet(new HashSet<>());
        giftCertificateDto.setName("Unlimited candy supply");
        when(giftCertificateRepository.getAllGiftCertificatesByRequirements(null, "candy suppl", null, null, null)).thenReturn(Collections.singletonList(giftCertificate));

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements(null, "candy suppl", null, null, null).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements(null, "candy suppl", null, null, null);
    }

    @Test
    public void shouldReturnGiftCertificateIfExistsByPartOfDescription(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("This certificate provides unlimited candy supply to the owner and owner only!");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setTagSet(new HashSet<>());
        giftCertificateDto.setDescription("This certificate provides unlimited candy supply to the owner and owner only!");

        when(giftCertificateRepository.getAllGiftCertificatesByRequirements(null, null, "candy suppl", null, null)).thenReturn(Collections.singletonList(giftCertificate));

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements(null, null, "candy suppl", null, null).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements(null, null, "candy suppl", null, null);
    }

    @Test
    public void shouldReturnGiftCertificateIfExistsByTagName() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(1, "candy"));
        giftCertificate.setTagSet(tagSet);

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(1);
        Set<TagDto> tagDtoSet = new HashSet<>();
        tagDtoSet.add(new TagDto(1, "candy"));
        giftCertificateDto.setTagSet(tagDtoSet);

        when(giftCertificateRepository.getAllGiftCertificatesByRequirements("candy", null, null, null, null)).thenReturn(Collections.singletonList(giftCertificate));

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.getAllGiftCertificatesByRequirements("candy", null, null, null, null).get(0));

        verify(giftCertificateRepository).getAllGiftCertificatesByRequirements("candy", null, null, null, null);
    }

    @Test
    public void shouldAddGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        long id = 1;
        giftCertificate.setId(id);
        giftCertificateDto.setId(id);

        Set<TagDto> tagDtoSet = new HashSet<>();
        giftCertificateDto.setTagSet(tagDtoSet);

        Set<Tag> tagSet = new HashSet<>();
        giftCertificate.setTagSet(tagSet);

        when(giftCertificateRepository.addGiftCertificate(giftCertificate)).thenReturn(giftCertificate);
        when(tagRepository.fetchAndAddNewTags(tagSet)).thenReturn(tagSet);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.addGiftCertificate(giftCertificateDto));

        verify(giftCertificateRepository).addGiftCertificate(giftCertificate);
    }

    @Test
    public void shouldThrowExceptionIfOnlySortTypeSpecified() {
        Assertions.assertThrows(InvalidRecordException.class, () -> giftCertificateService.getAllGiftCertificatesByRequirements(null, null, null, null, "name"));
    }

    @Test
    public void shouldThrowExceptionIfOnlySortOrderSpecified() {
        Assertions.assertThrows(InvalidRecordException.class, () -> giftCertificateService.getAllGiftCertificatesByRequirements(null, null, null,  "asc", null));
    }

    @Test
    public void shouldUpdateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        long id = 1;
        giftCertificate.setId(id);
        giftCertificateDto.setId(id);

        Set<TagDto> tagDtoSet = new HashSet<>();
        giftCertificateDto.setTagSet(tagDtoSet);

        Set<Tag> tagSet = new HashSet<>();
        giftCertificate.setTagSet(tagSet);

        when(giftCertificateRepository.updateGiftCertificateFull(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertificateRepository.existsGiftCertificateById(id)).thenReturn(true);
        when(tagRepository.fetchAndAddNewTags(tagSet)).thenReturn(tagSet);

        Assertions.assertEquals(giftCertificateDto, giftCertificateService.updateGiftCertificateFull(String.valueOf(id), giftCertificateDto));
    }
}