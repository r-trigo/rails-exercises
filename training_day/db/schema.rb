# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20170608105528) do

  create_table "armario_intervencoes_armario_necessidades", id: false, force: :cascade do |t|
    t.integer "armario_intervenco_id", null: false
    t.integer "armario_necessidade_id", null: false
  end

  create_table "armarios", force: :cascade do |t|
    t.string "ci_armario"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "tipo"
  end

  create_table "communities", force: :cascade do |t|
    t.string "name"
    t.text "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "communities_people", id: false, force: :cascade do |t|
    t.integer "person_id", null: false
    t.integer "community_id", null: false
  end

  create_table "fotos", force: :cascade do |t|
    t.string "nome"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "image"
    t.string "imageable_type"
    t.integer "imageable_id"
    t.index ["imageable_type", "imageable_id"], name: "index_fotos_on_imageable_type_and_imageable_id"
  end

  create_table "people", force: :cascade do |t|
    t.string "name"
    t.string "email"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "pts", force: :cascade do |t|
    t.string "ci_pt"
    t.string "tipo"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "torneio_equipamentos", force: :cascade do |t|
    t.string "nome"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "torneio_equipamentos_jogadors", id: false, force: :cascade do |t|
    t.integer "torneio_equipamento_id", null: false
    t.integer "torneio_jogador_id", null: false
  end

  create_table "torneio_jogadors", force: :cascade do |t|
    t.string "nome"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

end
