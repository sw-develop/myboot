package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {

    @Test
    void testWithSpringContainerProxyBeanMethodTrue() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        //when
        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        //then
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Test
    void testWithSpringContainerProxyBeanMethodFalse() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig2.class);
        ac.refresh();

        //when
        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        //then
        Assertions.assertThat(bean1.common).isNotEqualTo(bean2.common);
    }

    @Configuration
    static class MyConfig {

        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class MyConfig2 {

        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    private static class Common {
    }

    private static class Bean1 {

        private Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }

    private static class Bean2 {

        private Common common;

        public Bean2(Common common) {
            this.common = common;
        }
    }

    @Test
    void testWithoutSpringContainer() {
        //given
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        //when
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        //then
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {

        private Common common;

        @Override
        Common common() {
            if (this.common == null) this.common = super.common();
            return this.common;
        }
    }
}
