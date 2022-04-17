package cn.monkey.data.proxy.context;

import cn.monkey.data.proxy.router.RouteConfigProperties;
import cn.monkeyframework.common.spring.context.BeanContainer;
import graphql.GraphQL;
import graphql.language.SchemaExtensionDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class GraphQLContainer implements BeanContainer<GraphQL>, InitializingBean {

    private final RouteConfigProperties routeConfigProperties;

    public GraphQLContainer(RouteConfigProperties routeConfigProperties) {
        this.routeConfigProperties = routeConfigProperties;
    }

    @Override
    public GraphQL getBean(String name) {
        return null;
    }

    @Override
    public Collection<GraphQL> getAll() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        File[] schemaFiles = SchemaUtils.getSchemaFiles(this.routeConfigProperties.getSchemaDir());
        for (File f : schemaFiles) {
            SchemaParser schemaParser = new SchemaParser();
            TypeDefinitionRegistry parse = schemaParser.parse(f);
            List<SchemaExtensionDefinition> schemaExtensionDefinitions = parse.getSchemaExtensionDefinitions();
            SchemaGenerator schemaGenerator = new SchemaGenerator();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(parse, null);
            GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        }
    }
}
