package com.company.textfinder.service;

import com.company.textfinder.model.ProductModel;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TextFinderServiceTest {

    @InjectMocks
    private TextFinderService textFinderService;

    @Test(expected = RuntimeException.class)
    public void findTextTest_WithNullArgumentText() {
        //When
        textFinderService.findText(null, Collections.singletonList(new ProductModel("1", "some text")));
    }

    @Test(expected = RuntimeException.class)
    public void findTextTest_WithEmptyArgumentText() {
        //When
        textFinderService.findText("", Collections.singletonList(new ProductModel("1", "some text")));
    }

    @Test(expected = RuntimeException.class)
    public void findTextTest_WithNullArgumentList() {
        //When
        textFinderService.findText("some text", null);
    }

    @Test(expected = RuntimeException.class)
    public void findTextTest_WithEmptyArgumentList() {
        //When
        textFinderService.findText("some text", Collections.emptyList());
    }

    @Test
    public void findTextTest_withSimpleExample() {
        //Given
        ProductModel item = new ProductModel("1", "I have a blue car");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "blue";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withWordInsideOtherGreatherWord() {
        //Given
        ProductModel item = new ProductModel("1", "I have a lot of rice for my lunch");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "ice";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, empty());
    }

    @Test
    public void findTextTest_withMoreThanOneAppear() {
        //Given
        ProductModel item = new ProductModel("1", "Yesterday I eat icecream with my parents, "
                + "today I'm going to eat more ice with my friends.");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "ice";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withSearchTextHasTwoWords() {
        //Given
        ProductModel item = new ProductModel("1", "I am a software QA for new releases on my job");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "for job";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withSearchTextHasTwoWordsOneDoesNotExists() {
        //Given
        ProductModel item = new ProductModel("1", "I am a software QA for new releases on my job");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "for cat";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, empty());
    }

    @Test
    public void findTextTest_withMessWordsInPhrase() {
        //Given
        ProductModel item = new ProductModel("1", "Your solution is public and available from recent codes page for other users. "
                + "You can at any time change visibility of the code by click on icons above. "
                + "Learn more about visibility of the code.");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "code and";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withWordEndsInDot() {
        //Given
        ProductModel item = new ProductModel("1", "I have a blue car.");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "car";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withWordEndsThreeDots() {
        //Given
        ProductModel item = new ProductModel("1", "I have a blue car...");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "car";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withMeasureWord() {
        //Given
        ProductModel item = new ProductModel("1", "The backbone size is around 5\" long");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "5\"";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withSpecialChar() {
        //Given
        ProductModel item = new ProductModel("1", "One line with some words...");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "MY_SPECIAL_CHAR";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, empty());
    }

    @Test
    public void findTextTest_withSpecialCharAndQuotes() {
        //Given
        ProductModel item = new ProductModel("1", "One line with some words... and one \" quote");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = UUID.randomUUID().toString();

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, empty());
    }

    @Test
    public void findTextTest_withMinusChar() {
        //Given
        ProductModel item = new ProductModel("1", "One line with some words and -1 number");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "-1";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withNoCaseSensitive() {
        //Given
        ProductModel item = new ProductModel("1", "One line with some words and -1 number");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "Line SOME numBER";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText, Boolean.FALSE);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withLargeTextAndMultipleSearchText() {
        //Given
        ProductModel item = new ProductModel("1", "Java StringBuilder tutorial shows how to use StringBuilder in Java. "
                + "Java String objects are immutable; it is only possible to create mofied copies "
                + "of the original string. When we need to modify strings in-place, we use StringBuilder");
        List<ProductModel> collectionText = Collections.singletonList(item);
        List<ProductModel> collectionResult;
        String searchText = "are it strings we in";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, hasItem(item));
    }

    @Test
    public void findTextTest_withMultipleLargeTextAndMultipleSearchText() {
        //Given
        ProductModel[] items = new ProductModel[]{new ProductModel("1", "The projectiles flew 70 to 200 kilometers (43 to 125 miles) before "
            + "crashing into the sea, officials said, adding that both South Korean and US authorities "
            + "were analyzing details for further insight. The launch took place around at 9:06 a.m. "
            + "(8:06 p.m. ET Friday)."),
            //
            new ProductModel("2", "\"At present, our military has intensified surveillance and vigilance to prepare for North Korea's "
            + "additional launches,\" South Korea's Joint Chiefs of Staff said in a statement, adding that "
            + "Seoul and Washington were \"working closely together and maintaining their full preparedness.\""),
            //
            new ProductModel("3", "An earlier statement from South Korea's Defense Ministry said a \"missile\" had been fired."),
            new ProductModel("4", "It comes a few weeks after North Korea said it conducted a tactical guided weapons firing test, "
            + "according to state media. In a report from the Korean Central News Agency (KCNA), "
            + "leader Kim Jong Un praised that test as a \"great historic event in strengthening the combat "
            + "capability of the People's Army.\""),
            //
            new ProductModel("5", "The projectiles fired"),
            new ProductModel("6", "on Saturday appear to"),
            new ProductModel("7", "be much smaller however"),
            new ProductModel("8", "Analysts say"),
            new ProductModel("9", "unlikely to fall")};

        List<ProductModel> collectionText = Arrays.asList(items);
        List<ProductModel> collectionResult;
        String searchText = "said a of that";

        //When
        collectionResult = textFinderService.findText(searchText, collectionText);

        //Then
        Assert.assertNotNull(collectionResult);
        Assert.assertThat(collectionResult, not(empty()));
        Assert.assertThat(collectionResult.size(), is(2));
        Assert.assertThat(collectionResult, hasItem(items[1]));
    }

}
