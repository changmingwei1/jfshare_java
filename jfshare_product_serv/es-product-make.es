  curl -XPOST 10.45.182.179:9200/product_info -d '{
      "settings" : {
        "number_of_replicas": "1",
        "number_of_shards": "5"
      },
      "mappings" : {
          "ESProductInfo" : {
              "properties" : {
                  "productId" : { "type" : "string", "index" : "not_analyzed" },
                  "name" : { "type" : "string", "index" : "analyzed" }
              }
          }
      }
  }'
  
  
  
  curl -XPOST http://10.45.182.179:9200/product_info/ESProductInfo/_search -d '{"query":{"bool":{"must":[],"must_not":[],"should":[{"query_string":{"default_field":"ESProductInfo.name","query":"现代"}}]}},"from":0,"size":10,"sort":[],"facets":{}}'
  
  
  
  服务配置说明：
  
  es中增加配置，es支持跨域
  http.cors.enabled: true
  