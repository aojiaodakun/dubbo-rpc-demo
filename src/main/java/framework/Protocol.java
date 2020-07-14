package framework;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Protocol {

    void start(URL url);

    String send(URL url,Invocation invocation);
}
