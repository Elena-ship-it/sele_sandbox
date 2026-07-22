package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Старт теста: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Тест успешно пройден: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Тест провален: " + result.getName());
        // Автоматически делаем вложение скриншота при падении
        AllureUtils.attachScreenshot("Скриншот в момент падения теста " + result.getName());
        if (result.getThrowable() != null) {
            AllureUtils.attachLog("Текст ошибки (Stacktrace)", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Тест пропущен: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Запуск сьюта: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Завершение сьюта: " + context.getName());
    }
}
