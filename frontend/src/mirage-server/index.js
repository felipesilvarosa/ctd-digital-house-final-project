import { createServer, Model, JSONAPISerializer, Response } from "miragejs";

// fixtures
import categories from "./categories.json";
import users from "./users.json";
import destinations from "./destinations.json";
import products from "./products.json";

export function makeServer({ environment = "development" } = {}) {
  console.log("running miragejs server...");
  let server = createServer({
    environment,

    serializers: {
      application: JSONAPISerializer,
      products: JSONAPISerializer.extend({
        normalize(json) {
          return {
            data: {
              type: "product",
              attributes: json,
            },
          };
        },
      }),
      category: JSONAPISerializer.extend({
        normalize(json) {
          return {
            data: {
              type: "category",
              attributes: json,
            },
          };
        },
      }),
      user: JSONAPISerializer.extend({
        normalize(json) {
          return {
            data: {
              type: "user",
              attributes: json,
            },
          };
        },
      }),
      destination: JSONAPISerializer.extend({
        normalize(json) {
          return {
            data: {
              type: "destination",
              attributes: json,
            },
          };
        },
      }),
    },

    models: {
      category: Model,
      user: Model,
      destination: Model,
      products: Model,
    },

    fixtures: {
      categories,
      users,
      destinations,
      products,
    },

    timing: 1000,

    routes() {
      this.namespace = "api";

      this.get("/destinations");
      this.get("/products");
      this.get("/products/:id");
      this.get("/categories");
      this.post("/login", (schema, request) => {
        const body = JSON.parse(request.requestBody)
        const userFound = schema.db.users.findBy({email: body.email})
        if (!userFound || userFound.password !== body.password) {
          return new Response(401, {}, { error: "Credenciais inválidas." })
        }

        const filteredUser = { ...userFound }
        if (filteredUser.password) {
          delete filteredUser.password
        }

        return new Response(200, {}, { user: filteredUser })
      });
      this.post("/users", (schema, request) => {
        const body = JSON.parse(request.requestBody)
        const userExists = schema.db.users.findBy({email: body.email})
        if(userExists) {
          return new Response(401, {}, { error: "Usuário já cadastrado" })
        }

        const newUser = schema.users.create(body)
        return new Response(201, {}, newUser)
      });
    },
  });
  return server;
}
