FROM rabbitmq:3-management-alpine

ADD init.sh /init.sh
#ADD ./etc/definitions.json /definitions.json
#ADD ./plugins/rabbitmq_message_timestamp-3.8.0.ez /opt/rabbitmq/plugins/rabbitmq_message_timestamp-3.8.0.ez

RUN chmod +x /init.sh
EXPOSE 15672
EXPOSE 5672

# Define default command
CMD ["/init.sh"]

# 960ZUV9ive6jlrpuhqsz0TlC
