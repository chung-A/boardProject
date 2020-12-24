package com.chung.board.prototype;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class SingletonWithPrototypeTest {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        Assertions.assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();
        Assertions.assertThat(bean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototypeBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class,PrototypeBean.class);
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        int count = singletonBean1.logic();
        Assertions.assertThat(count).isEqualTo(1);

        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        int count2 = singletonBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class SingletonBean {

        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            System.out.println("사용할 prototypeBean 정보!: "+prototypeBean);

            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

        @PostConstruct
        void init() {
            System.out.println("PrototypeBean.init"+this);
        }

        @PreDestroy
        void preDestroy() {
            System.out.println("PrototypeBean.preDestroy"+this);
        }

    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count=0;

        @PostConstruct
        void init() {
            System.out.println("PrototypeBean.init"+this);
        }

        @PreDestroy
        void preDestroy() {
            System.out.println("PrototypeBean.preDestroy"+this);
        }

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
