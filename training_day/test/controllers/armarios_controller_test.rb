require 'test_helper'

class ArmariosControllerTest < ActionDispatch::IntegrationTest
  setup do
    @armario = armarios(:one)
  end

  test "should get index" do
    get armarios_url
    assert_response :success
  end

  test "should get new" do
    get new_armario_url
    assert_response :success
  end

  test "should create armario" do
    assert_difference('Armario.count') do
      post armarios_url, params: { armario: { ci_armario: @armario.ci_armario } }
    end

    assert_redirected_to armario_url(Armario.last)
  end

  test "should show armario" do
    get armario_url(@armario)
    assert_response :success
  end

  test "should get edit" do
    get edit_armario_url(@armario)
    assert_response :success
  end

  test "should update armario" do
    patch armario_url(@armario), params: { armario: { ci_armario: @armario.ci_armario } }
    assert_redirected_to armario_url(@armario)
  end

  test "should destroy armario" do
    assert_difference('Armario.count', -1) do
      delete armario_url(@armario)
    end

    assert_redirected_to armarios_url
  end
end
