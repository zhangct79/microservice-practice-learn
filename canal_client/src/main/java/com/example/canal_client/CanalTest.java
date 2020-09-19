package com.example.canal_client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.sun.deploy.xml.GeneralEntity;

import java.net.InetSocketAddress;
import java.util.List;
public class CanalTest {

    public static void main(String[] args) throws Exception {
//canal.ip = 192.168.56.104
//canal.port = 11111
//canal.destinations = example
//canal.user =
//canal.passwd =
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.112.151", 11111), "example", "", "");
        try {
            connector.connect();
            //监听的表，    格式为数据库.表名,数据库.表名
            connector.subscribe(".*\\..*");
//            connector.subscribe("canal_test.users,canal_test.test");
            connector.rollback();

            while (true) {
                Message message = connector.getWithoutAck(100); // 获取指定数量的数据
                long batchId = message.getId();
                if (batchId == -1 || message.getEntries().isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }
                printEntry(message.getEntries());
                connector.ack(batchId);// 提交确认，消费成功，通知server删除数据
                // connector.rollback(batchId);// 处理失败, 回滚数据，后续重新获取数据
            }
        }catch (Exception e){

        }finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            System.out.println("getGtid"+entry.getHeader().getGtid());

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}