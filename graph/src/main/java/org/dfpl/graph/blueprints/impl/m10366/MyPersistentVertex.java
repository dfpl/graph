package org.dfpl.graph.blueprints.impl.m10366;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Set;

import org.bson.Document;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyPersistentVertex implements Vertex {

	private MyMongoDB md;

	private String id;

	public MyPersistentVertex(String id, MyMongoDB md) {
		this.md = md;
		this.id = id;

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			Document propertyDoc = (Document) this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first()
					.get("property");
			return propertyDoc.get(key);
		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}

	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub

		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			Document propertyDoc = (Document) this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first()
					.get("property");

			return propertyDoc.keySet();

		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}

	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", id), Updates.set("property",
					((Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("property"))
							.append(key, value)));
		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			Document propertyDoc = (Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first()
					.get("property");

			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", id), Updates.set("property",
					((Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("property"))
							.remove(key)));

			return propertyDoc;
		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			ArrayList<Edge> edges = new ArrayList<>();

			if (labels.length != 0) {

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}
					}

				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					inEdgeIDs.addAll(outEdgeIDs);
					ArrayList<String> bothEdgeIDs = inEdgeIDs;

					if (bothEdgeIDs.isEmpty()) {
						// System.out.println("not has both edge");
					} else {
						for (String edgeID : bothEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}
					}

				}

			} else {
				// label이 없을 경우

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					}
					{
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							edges.add(edge);

						}
					}

				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							edges.add(edge);

						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					inEdgeIDs.addAll(outEdgeIDs);
					ArrayList<String> bothEdgeIDs = inEdgeIDs;

					if (bothEdgeIDs.isEmpty()) {

						// System.out.println("not has both edge");
					} else {
						for (String edgeID : bothEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							edges.add(edge);

						}
					}

				}

			}

			return edges;

		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}

	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			ArrayList<Edge> edges = new ArrayList<>();
			ArrayList<Vertex> vertecies = new ArrayList<>();

			if (labels.length != 0) {

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									vertecies.add(edge.getVertex(Direction.IN));
								}

							}

						}
					}

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							for (String label : labels) {

								if (edgeDoc.get("label").equals(label)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									vertecies.add(edge.getVertex(Direction.OUT));
								}

							}

						}
					}

				}

			} else {
				// label이 없을 경우

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");

					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							edges.add(edge);

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

				} else if (direction == Direction.OUT) {

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");

					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							edges.add(edge);

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
									(String) edgeDoc.get("label"), md);
							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				}

			}

			return vertecies;

		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			ArrayList<Edge> edges = new ArrayList<>();
			ArrayList<Vertex> vertecies = new ArrayList<>();

			if (labels.length != 0) {

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							String inVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("source");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondInEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", inVertexID)).first().get("inEdgeIDs");

							if (secondInEdgeIDs.isEmpty()) {
								// System.out.println("not has in edge");
							} else {
								for (String secondID : secondInEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									for (String label : labels) {

										if (secondEdgeDoc.get("label").equals(label)) {

											MyPersistentEdge edge = new MyPersistentEdge(
													(String) secondEdgeDoc.get("_id"),
													(String) secondEdgeDoc.get("label"), md);
											edges.add(edge);
										}

									}

								}

							}

						}
						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}

					}
				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							String outVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("target");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondOutEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", outVertexID)).first().get("outEdgeIDs");

							if (secondOutEdgeIDs.isEmpty()) {
								// System.out.println("not has out edge");
							} else {
								for (String secondID : secondOutEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									for (String label : labels) {

										if (secondEdgeDoc.get("label").equals(label)) {

											MyPersistentEdge edge = new MyPersistentEdge(
													(String) secondEdgeDoc.get("_id"),
													(String) secondEdgeDoc.get("label"), md);
											edges.add(edge);
										}

									}

								}

							}

						}
						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}

					}

				} else {// both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							String inVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("source");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondInEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", inVertexID)).first().get("inEdgeIDs");

							if (secondInEdgeIDs.isEmpty()) {
								// System.out.println("not has in edge");
							} else {
								for (String secondID : secondInEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									for (String label : labels) {

										if (secondEdgeDoc.get("label").equals(label)) {

											MyPersistentEdge edge = new MyPersistentEdge(
													(String) secondEdgeDoc.get("_id"),
													(String) secondEdgeDoc.get("label"), md);
											edges.add(edge);
										}

									}

								}

							}

						}
						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}

					}

					edges = new ArrayList<Edge>();

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							String outVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("target");
							@SuppressWarnings("unchecked")
							ArrayList<String> secondOutEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", outVertexID)).first().get("outEdgeIDs");

							if (secondOutEdgeIDs.isEmpty()) {
								// System.out.println("not has out edge");
							}
							{
								for (String secondID : secondOutEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									for (String label : labels) {

										if (secondEdgeDoc.get("label").equals(label)) {

											MyPersistentEdge edge = new MyPersistentEdge(
													(String) secondEdgeDoc.get("_id"),
													(String) secondEdgeDoc.get("label"), md);
											edges.add(edge);
										}

									}

								}

							}

						}
						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}

					}

				}

			} else { // label 없을 경우

				if (direction == Direction.IN) {

					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							String inVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("source");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondInEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", inVertexID)).first().get("inEdgeIDs");

							if (secondInEdgeIDs.isEmpty()) {
								// System.out.println("not has in edge");
							} else {
								for (String secondID : secondInEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									MyPersistentEdge edge = new MyPersistentEdge((String) secondEdgeDoc.get("_id"),
											(String) secondEdgeDoc.get("label"), md);
									edges.add(edge);

								}
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

				} else if (direction == Direction.OUT) {

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							String outVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("target");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondOutEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", outVertexID)).first().get("outEdgeIDs");

							if (secondOutEdgeIDs.isEmpty()) {

								// System.out.println("not has out edge");

							} else {
								for (String secondID : secondOutEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									MyPersistentEdge edge = new MyPersistentEdge((String) secondEdgeDoc.get("_id"),
											(String) secondEdgeDoc.get("label"), md);
									edges.add(edge);

								}
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				} else {

					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							String inVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("source");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondInEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", inVertexID)).first().get("inEdgeIDs");

							if (secondInEdgeIDs.isEmpty()) {
								// System.out.println("not has in edge");
							}
							{
								for (String secondID : secondInEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									MyPersistentEdge edge = new MyPersistentEdge((String) secondEdgeDoc.get("_id"),
											(String) secondEdgeDoc.get("label"), md);
									edges.add(edge);

								}
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

					edges = new ArrayList<Edge>();

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							String outVertexID = (String) this.md.getCollection("edge").find(Filters.eq("_id", edgeID))
									.first().get("target");

							@SuppressWarnings("unchecked")
							ArrayList<String> secondOutEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
									.find(Filters.eq("_id", outVertexID)).first().get("outEdgeIDs");

							if (secondOutEdgeIDs.isEmpty()) {
								// System.out.println("not has out edge");
							} else {

								for (String secondID : secondOutEdgeIDs) {

									Document secondEdgeDoc = this.md.getCollection("edge")
											.find(Filters.eq("_id", secondID)).first();

									MyPersistentEdge edge = new MyPersistentEdge((String) secondEdgeDoc.get("_id"),
											(String) secondEdgeDoc.get("label"), md);
									edges.add(edge);

								}
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				}

			}

			return vertecies;
		} else {

			throw new IllegalArgumentException("no exist vertex id");
		}

	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {

		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			ArrayList<Edge> edges = new ArrayList<>();
			ArrayList<Vertex> vertecies = new ArrayList<>();

			if (labels.length != 0) {

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							for (String label : labels) {

								if (edgeDoc.get("label").equals(label) && propertyDoc.get(key).equals(value)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							for (String label : labels) {

								if (edgeDoc.get("label").equals(label) && propertyDoc.get(key).equals(value)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									edges.add(edge);
								}

							}
						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							for (String label : labels) {

								if (edgeDoc.get("label").equals(label) && propertyDoc.get(key).equals(value)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									vertecies.add(edge.getVertex(Direction.IN));
								}

							}

						}
					}

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							for (String label : labels) {

								if (edgeDoc.get("label").equals(label) && propertyDoc.get(key).equals(value)) {

									MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
											(String) edgeDoc.get("label"), md);
									vertecies.add(edge.getVertex(Direction.OUT));
								}

							}

						}
					}

				}

			} else {
				// label이 없을 경우

				if (direction == Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							if (propertyDoc.get(key).equals(value)) {
								MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
										(String) edgeDoc.get("label"), md);
								edges.add(edge);
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.IN));

						}
					}

				} else if (direction == Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							if (propertyDoc.get(key).equals(value)) {
								MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
										(String) edgeDoc.get("label"), md);
								edges.add(edge);
							}

						}

						for (Edge edge : edges) {

							vertecies.add(edge.getVertex(Direction.OUT));

						}
					}

				} else { // both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs = (ArrayList<String>) this.md.getCollection("vertex")
							.find(Filters.eq("_id", id)).first().get("outEdgeIDs");

					if (inEdgeIDs.isEmpty()) {
						// System.out.println("not has in edge");
					} else {
						for (String edgeID : inEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							if (propertyDoc.get(key).equals(value)) {
								MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
										(String) edgeDoc.get("label"), md);
								edges.add(edge);
							}

						}
					}

					if (outEdgeIDs.isEmpty()) {
						// System.out.println("not has out edge");
					} else {
						for (String edgeID : outEdgeIDs) {

							Document edgeDoc = this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();

							Document propertyDoc = (Document) this.md.getCollection("edge")
									.find(Filters.eq("_id", edgeID)).first().get("property");

							if (propertyDoc.get(key).equals(value)) {
								MyPersistentEdge edge = new MyPersistentEdge((String) edgeDoc.get("_id"),
										(String) edgeDoc.get("label"), md);
								edges.add(edge);
							}

						}
					}

				}

			}

			return vertecies;

		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first() != null) {

			this.md.getCollection("vertex").findOneAndDelete(Filters.eq("_id", this.id));

		} else {
			throw new IllegalArgumentException("no exist vertex id");
		}

	}

}
